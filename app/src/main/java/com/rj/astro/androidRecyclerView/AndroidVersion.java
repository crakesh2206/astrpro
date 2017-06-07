package com.rj.astro.androidRecyclerView;

public class AndroidVersion {
    String feed_id;
    String name;
    String email;
    String mobile;
    String feedback;
    String usertype;
    String user_id;

    public String getFeed_id() {
        return feed_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getUsertype() {
        return usertype;
    }

    public String getUser_id() {
        return user_id;
    }

    public AndroidVersion(String feed_id, String name, String email, String mobile, String feedback, String usertype, String user_id){
     this.feed_id = feed_id;
     this.name = name;
     this.email = email;
        this.mobile = mobile;
        this.feedback = feedback;
        this.usertype = usertype;
        this.user_id = user_id;
 }

}
