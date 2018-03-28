package com.mylove.mlive.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Administrator on 2018/3/2.
 */
@Entity(indexes = {
        @Index(value = "name, id DESC", unique = true)
})
public class ChannelBean implements Parcelable {
    /**
     * id : 1
     * name : 中央频道
     * area :
     * list : [{"number":"1","name":"CCTV1综合","epg":"cntv-cctv1","streams":[{"url":"cntvhttp://pa://cctv_p2p_hdcctv1","mode":""},{"url":"http://live.0755tv.net/b/live.php?src=cmv&id=cctv1&pwd=yuntutv","mode":""}]},{"number":"2","name":"CCTV2财经","epg":"cntv-cctv2","streams":[{"url":"cntvhttp://pa://cctv_p2p_hdcctv2","mode":""},{"url":"http://live.0755tv.net/b/live.php?src=cmv&id=cctv2&pwd=yuntutv","mode":""}]}]
     */
    @Id
    private Long id;

    @NotNull
    private String name;

    private String area;

    @ToMany(referencedJoinProperty = "channelId")
    private List<ListBean> list;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 749614743)
    public List<ListBean> getList() {
        if (list == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ListBeanDao targetDao = daoSession.getListBeanDao();
            List<ListBean> listNew = targetDao._queryChannelBean_List(id);
            synchronized (this) {
                if (list == null) {
                    list = listNew;
                }
            }
        }
        return list;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 589833612)
    public synchronized void resetList() {
        list = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    public ChannelBean() {
    }

    @Generated(hash = 427181393)
    public ChannelBean(Long id, @NotNull String name, String area) {
        this.id = id;
        this.name = name;
        this.area = area;
    }

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 903675195)
    private transient ChannelBeanDao myDao;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.area);
        dest.writeTypedList(this.list);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1095501526)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getChannelBeanDao() : null;
    }

    protected ChannelBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.area = in.readString();
        this.list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Creator<ChannelBean> CREATOR = new Creator<ChannelBean>() {
        @Override
        public ChannelBean createFromParcel(Parcel source) {
            return new ChannelBean(source);
        }

        @Override
        public ChannelBean[] newArray(int size) {
            return new ChannelBean[size];
        }
    };
}
