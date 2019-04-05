package mpp.vlad_dani.server.repository;

import mpp.vlad_dani.common.domain.Movie;
import mpp.vlad_dani.common.domain.validator.MovieValidator;

public class MovieXmlRepository extends XmlRepo<Integer, Movie> {
    public MovieXmlRepository(){
        super(new MovieValidator(),"moviestore.xml");
        try{
            this.loadContent(Movie.class);}catch (Exception e){e.printStackTrace();}
    }
}
