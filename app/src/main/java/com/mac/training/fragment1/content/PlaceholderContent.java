package com.mac.training.fragment1.content;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 8/23/2016.
 */
public class PlaceholderContent {

    public static class PlaceholderItem implements Parcelable {
        private int id;
        private int userId;
        private String title;
        private String body;

        public static final Parcelable.Creator<PlaceholderItem> CREATOR =
                new Parcelable.Creator<PlaceholderItem>() {
                    @Override
                    public PlaceholderItem createFromParcel(Parcel parcel) {
                        return new PlaceholderItem(parcel);
                    }

                    @Override
                    public PlaceholderItem[] newArray(int size) {
                        return new PlaceholderItem[size];
                    }
                };

        private PlaceholderItem(Parcel in) {
            id = in.readInt();
            userId = in.readInt();
            title = in.readString();
            body = in.readString();
        }

        public PlaceholderItem(int id, int userId, String title, String body) {
            this.id = id;
            this.userId = userId;
            this.title = title;
            this.body = body;
        }

        public int getItemId() {
            return id;
        }

        public void setItemId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeInt(id);
            parcel.writeInt(userId);
            parcel.writeString(title);
            parcel.writeString(body);
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append("id: " + id + " userId: " + userId + " title: " + title + " body: " + body);
            return sb.toString();
        }
    }

    private List<PlaceholderItem> items;
    private Map<Integer, PlaceholderItem> itemsMap;

    public PlaceholderContent() {
        items = new ArrayList<>();
        itemsMap = new HashMap<>();
    }

    public void addItem(PlaceholderItem item) {
        items.add(item);
        itemsMap.put(item.id, item);
    }

    public List<PlaceholderItem> getAllItems() {
        return items;
    }

    public void clearAllItems() { items.clear(); }
}
