package mpp.vlad_dani.server.repository;

import mpp.vlad_dani.common.domain.Movie;
import mpp.vlad_dani.common.domain.validator.MovieValidator;

import java.util.List;

public class MovieRepository extends AbstractFileRepo<Integer, Movie> {
    public MovieRepository(){
        super(new MovieValidator(),"movies.txt");
    }

    @Override
    public String toFile(Movie entity){return entity.toFile();}

    public Movie createEntity(List<String> list){
        return new Movie(Integer.parseInt(list.get(0)),list.get(1), Integer.parseInt(list.get(2)),list.get(3), Integer.parseInt(list.get(4)), Float.parseFloat(list.get(5)));
    }
}
