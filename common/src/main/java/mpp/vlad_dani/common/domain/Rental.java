package mpp.vlad_dani.common.domain;

import java.util.Date;

public class Rental extends BaseEntity<Integer>{
    private Integer movieID;
    private Integer clientID;
    private Date date;

    public Rental(){}
    public Rental(Integer mid, Integer cid, Integer rid){
        super.setId(rid);
        date=new Date();
        movieID= mid;
        clientID=cid;
    }

    public void setId(Integer id)
    {
        super.setId(id);
    }
    public void setMovie(Integer id){
        this.movieID=id;
    }
    public void setClient(Integer id){this.clientID=id;}
    public Integer getMovie(){return movieID;}

    public Integer getClient(){return clientID;}

    public Date getDate(){return date;}

    @Override
    public String toString(){
        return "MovieID: "+movieID+" ClientID: "+clientID+" Date: "+new Date();
    }

    public String toFile(){
        return ""+this.getId()+","+movieID+","+clientID+","+date;
    }
}
