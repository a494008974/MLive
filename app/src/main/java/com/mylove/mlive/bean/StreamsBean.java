package com.mylove.mlive.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/3/2.
 */
@Entity(indexes = {
        @Index(value = "url, id DESC", unique = true)
})
public class StreamsBean implements Parcelable {
    /**
     * url : cntvhttp://pa://cctv_p2p_hdcctv1
     * mode :
     */
    @Id(autoincrement = true)
    private Long id;

    private Long listId;

    public Long getListId() {
        return listId;
    }
    public void setListId(Long listId) {
        this.listId = listId;
    }

    private String url;
    private String mode;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StreamsBean() {
    }

    @Generated(hash = 758828408)
    public StreamsBean(Long id, Long listId, String url, String mode) {
        this.id = id;
        this.listId = listId;
        this.url = url;
        this.mode = mode;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.listId);
        dest.writeString(this.url);
        dest.writeString(this.mode);
    }

    protected StreamsBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.listId = (Long) in.readValue(Long.class.getClassLoader());
        this.url = in.readString();
        this.mode = in.readString();
    }

    public static final Creator<StreamsBean> CREATOR = new Creator<StreamsBean>() {
        @Override
        public StreamsBean createFromParcel(Parcel source) {
            return new StreamsBean(source);
        }

        @Override
        public StreamsBean[] newArray(int size) {
            return new StreamsBean[size];
        }
    };
}
