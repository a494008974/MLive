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
        @Index(value = "name, number DESC", unique = true)
})
public class ListBean implements Parcelable {
    /**
     * number : 1
     * name : CCTV1综合
     * epg : cntv-cctv1
     * streams : [{"url":"cntvhttp://pa://cctv_p2p_hdcctv1","mode":""},{"url":"http://live.0755tv.net/b/live.php?src=cmv&id=cctv1&pwd=yuntutv","mode":""}]
     */
    @Id
    private Long number;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    private Long channelId;

    public Long getChannelId() {
        return channelId;
    }
    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    @NotNull
    private String name;
    private String epg;

    @ToMany(referencedJoinProperty = "listId")
    private List<StreamsBean> streams;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEpg() {
        return epg;
    }

    public void setEpg(String epg) {
        this.epg = epg;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.number);
        dest.writeValue(this.channelId);
        dest.writeString(this.name);
        dest.writeString(this.epg);
        dest.writeTypedList(this.streams);
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1261594781)
    public List<StreamsBean> getStreams() {
        if (streams == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StreamsBeanDao targetDao = daoSession.getStreamsBeanDao();
            List<StreamsBean> streamsNew = targetDao._queryListBean_Streams(number);
            synchronized (this) {
                if (streams == null) {
                    streams = streamsNew;
                }
            }
        }
        return streams;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 64246413)
    public synchronized void resetStreams() {
        streams = null;
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 141307482)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getListBeanDao() : null;
    }

    public ListBean() {
    }

    protected ListBean(Parcel in) {
        this.number = (Long) in.readValue(Long.class.getClassLoader());
        this.channelId = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.epg = in.readString();
        this.streams = in.createTypedArrayList(StreamsBean.CREATOR);
    }

    @Generated(hash = 1242038963)
    public ListBean(Long number, Long channelId, @NotNull String name, String epg) {
        this.number = number;
        this.channelId = channelId;
        this.name = name;
        this.epg = epg;
    }

    public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
        @Override
        public ListBean createFromParcel(Parcel source) {
            return new ListBean(source);
        }

        @Override
        public ListBean[] newArray(int size) {
            return new ListBean[size];
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 381411154)
    private transient ListBeanDao myDao;
}
