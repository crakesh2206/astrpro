package com.rj.astro.androidRecyclerView;

public class AndroidVersion {
    private String ver;
    private String name;
    private String api;
 public AndroidVersion(String ver,String name,String api){
     this.ver = ver;
     this.name = name;
     this.api = api;
 }
    public String getVer() {
        return ver;
    }

    public String getName() {
        return name;
    }

    public String getApi() {
        return api;
    }
}
