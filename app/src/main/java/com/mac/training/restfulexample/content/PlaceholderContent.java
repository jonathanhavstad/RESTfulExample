package com.mac.training.restfulexample.content;

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
        public int id;
        public int userId;
        public String title;
        public String body;

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

    public PlaceholderItem getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        throw new ArrayIndexOutOfBoundsException("Index " + index + " is out of bounds!");
    }

    public int getSize() {
        return items.size();
    }

    public void clearAllItems() { items.clear(); }
}
