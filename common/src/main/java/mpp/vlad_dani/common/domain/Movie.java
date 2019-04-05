package mpp.vlad_dani.common.domain;

public class Movie extends BaseEntity<Integer> {
    //id, title, year, genre, duration (minutes), rating, timesRented
    //attributes
    private String title;
    private Integer year;
    private String genre;
    private Integer duration;
    private Float rating;
    //
    public Movie(){}
    /**
     * Constructor with parameters
     * @param id
     * @param title
     * @param year
     * @param genre
     * @param duration
     * @param rating
     */
    public Movie(Integer id, String title, Integer year, String genre, Integer duration, Float rating){
        super.setId(id);
        this.title=title;
        this.year=year;
        this.genre=genre;
        this.duration=duration;
        this.rating=rating;
    }

    /**
     * Get the id of the movie
     * @return id
     */
    public Integer getId(){return super.getId();}

    /**
     * sets the id of the movie with the given id
     * @param id
     */
    public void setId(Integer id){super.setId(id);}

    /**
     * gets the title of the movie
     * @return title
     */
    public String getTitle(){return this.title;}

    /**
     * sets the title of movie with given id
     * @param title
     */
    public void setTitle(String title){this.title=title;}

    public Integer getYear(){return this.year;}
    public void setYear(Integer year){this.year=year;}

    public String getGenre(){return this.genre;}
    public void setGenre(String genres){this.genre=genres;}

    public Integer getDuration(){return this.duration;}
    public void setDuration(Integer duration){this.duration=duration;}

    public Float getRating(){return this.rating;}
    public void setRating(Float rating){this.rating=rating;}

    /**
     * Constructs a visual representation of the movie
     * @return a string repr for the movie
     */
    public String toString(){
        return "ID: "+super.getId()+", Title: "+title+", Year: "+year+", Genre: "+genre+", Duration: "+duration+", Rating: "+rating;
    }

    /**
     *
     * @return a string for soring data in a file
     */
    public String toFile(){
        return super.getId().toString()+","+title+","+year+","+genre+","+duration+","+rating;
    }
}
