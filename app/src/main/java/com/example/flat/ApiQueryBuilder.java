package com.example.flat;

import android.net.Uri;

import java.net.URL;

public class ApiQueryBuilder {
    public static final String ID = "id";
    public static final String ROOMTYPE = "room_type";
    public static final String LOCATION = "location";
    public static final String PRICE = "price";
    public static final String OWNER = "owner";
    public static final String SEARCH = "search";
    public static final String DESCRIPTION = "description";
    public  static final String BASE_API_URL = "http://35.208.96.236/rooms";

    private String mId;
    private String mRoomType;
    private String mLocation;
    private int mPrice;
    private String mDescription;
    private String mEndpoint;
    private String mSearch;
    private String mOwner;

    public URL buildUrl(){
        StringBuilder sb = new StringBuilder();
        if (this.mLocation != null) sb.append(LOCATION + "=" + mLocation + "&");
        if (this.mRoomType != null) sb.append(ROOMTYPE + "=" + mRoomType + "&");
        if (this.mPrice > 0) sb.append(PRICE + "=" + mPrice + "&");
        if (this.mDescription != null) sb.append(DESCRIPTION + "=" + mDescription + "&");
        if (this.mSearch != null) sb.append(SEARCH + "=" + mSearch + "&");
        if (this.mOwner != null) sb.append(OWNER + "=" + mOwner + "&");

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        String query = sb.toString();
        URL url = null;
        String fullUrl = BASE_API_URL + mEndpoint + "?" + query;
        Uri uri = Uri.parse(fullUrl);
        try {
            url = new URL(uri.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    private ApiQueryBuilder(QueryBuilder queryBuilder){
        this.mId = queryBuilder.mId;
        this.mRoomType = queryBuilder.mRoomType;
        this.mLocation = queryBuilder.mLocation;
        this.mPrice = queryBuilder.mPrice;
        this.mDescription = queryBuilder.mDescription;
        this.mEndpoint = queryBuilder.mEndpoint;
        this.mSearch = queryBuilder.mSearch;
        this.mOwner = queryBuilder.mOwner;
    }


    public static class QueryBuilder {
        private String mId;
        private String mRoomType;
        private String mLocation;
        private int mPrice = 0;
        private String mDescription;
        private String mEndpoint;
        private String mSearch;
        private String mOwner;

        public QueryBuilder(String endPoint){
            this.mEndpoint = endPoint;
        }

        public QueryBuilder id(String id){
            this.mId = id;
            return this;
        }

        public QueryBuilder mRoomType(String roomType){
            this.mRoomType = roomType;
            return this;
        }

        public QueryBuilder mLocation(String location){
            this.mLocation = location;
            return this;
        }

        public QueryBuilder mPrice(int price){
            this.mPrice = price;
            return this;
        }

        public QueryBuilder mDescription (String description){
            this.mDescription = description;
            return this;
        }
        public QueryBuilder mEndpoint (String endPoint){
            this.mEndpoint = endPoint;
            return this;
        }
        public QueryBuilder mSearch (String search){
            this.mSearch = search;
            return this;
        }
        public QueryBuilder mOwner (String owner){
            this.mOwner = owner;
            return this;
        }
        public ApiQueryBuilder build(){
            return new ApiQueryBuilder(this);
        }
    }
}
