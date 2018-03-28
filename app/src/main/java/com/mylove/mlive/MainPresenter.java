package com.mylove.mlive;

import android.util.Log;

import com.mylove.mlive.base.BasePresenter;
import com.mylove.mlive.bean.ChannelBean;
import com.mylove.mlive.bean.ChannelBeanDao;
import com.mylove.mlive.bean.DaoMaster;
import com.mylove.mlive.bean.ListBean;
import com.mylove.mlive.bean.StreamApi;
import com.mylove.mlive.bean.StreamsBean;
import com.mylove.mlive.net.BaseObserver;
import com.mylove.mlive.net.ChannelApi;
import com.mylove.mlive.net.RxSchedulers;
import com.mylove.mlive.utils.SharedPreferencesUtils;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2018/3/1.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    ChannelApi channelApi;

    @Inject
    public MainPresenter(ChannelApi channelApi){
        this.channelApi = channelApi;
    }

    @Override
    public void getChannel() {

        channelApi.getStream()
                .compose(RxSchedulers.<StreamApi>applySchedulers())
                .map(new Function<StreamApi, Boolean>() {
                    @Override
                    public Boolean apply(StreamApi streamApi) throws Exception {
                        String time = (String) SharedPreferencesUtils.getParam(MyApp.getInstance(),"EDIT","");
                        Log.d("aaaaaa","time = "+time + " netTime = "+streamApi.getEdit());
                        if(time.equals(streamApi.getEdit())){
                            Log.d("aaaaaa","getChannelFromDb");
                            getChannelFromDb();
                            return true;
                        }else{
                            Log.d("aaaaaa","getChannelFromNet");
                            getChannelFromNet(streamApi.getUrl());
                            SharedPreferencesUtils.setParam(MyApp.getInstance(),"EDIT",streamApi.getEdit());
                            return false;
                        }
                    }
                })
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {

                    }

                    @Override
                    public void onFail(Throwable e) {
                        Log.d("aaaaaa","onFail"+e.getMessage());
                    }
                });

    }

    private void getChannelFromDb() {
        MyApp.getInstance().getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                //数据库获取
                Query query = MyApp.getInstance().getDaoSession()
                        .queryBuilder(ChannelBean.class)
                        .orderAsc(ChannelBeanDao.Properties.Id)
                        .build();

                List<ChannelBean> channelBeans = query.list();
                mView.loadChannelData(channelBeans);
            }
        });
    }

    public void getChannelFromNet(String url){
        channelApi.getChannelDetail(url)
                .compose(RxSchedulers.<List<ChannelBean>>applySchedulers())
                .flatMap(new Function<List<ChannelBean>, Observable<ChannelBean>>() {
                    @Override
                    public Observable<ChannelBean> apply(final List<ChannelBean> channelBeans) throws Exception {
                        Log.d("channel","Size = "+channelBeans.size());

//                       入数据库
                        MyApp.getInstance().getDaoSession().runInTx(new Runnable() {
                            @Override
                            public void run() {
                                //清空数据库
                                DaoMaster.dropAllTables(MyApp.getInstance().getDaoMaster().getDatabase(),true);
                                DaoMaster.createAllTables(MyApp.getInstance().getDaoMaster().getDatabase(),true);

                                //插入数据
                                for (ChannelBean channelBean : channelBeans){
                                    MyApp.getInstance().getDaoSession().insertOrReplace(channelBean);
                                    for (ListBean listBean : channelBean.getList()){
                                        listBean.setChannelId(channelBean.getId());
                                        MyApp.getInstance().getDaoSession().insertOrReplace(listBean);
                                        for (StreamsBean streamsBean : listBean.getStreams()){
                                            streamsBean.setListId(listBean.getNumber());
                                            MyApp.getInstance().getDaoSession().insertOrReplace(streamsBean);
                                        }
                                    }
                                }
                            }
                        });

                        mView.loadChannelData(channelBeans);
                        return Observable.fromIterable(channelBeans);
                    }
                })
                .map(new Function<ChannelBean, List<ListBean>>() {
                    @Override
                    public List<ListBean> apply(ChannelBean channelBean) throws Exception {
                        return channelBean.getList();
                    }
                })
                .subscribe(new BaseObserver<List<ListBean>>() {
                    @Override
                    public void onSuccess(List<ListBean> listBeans) {
//                        mView.loadListData(listBeans);
                    }

                    @Override
                    public void onFail(Throwable e) {
                        getChannelFromDb();
                    }
                });
    }

}
