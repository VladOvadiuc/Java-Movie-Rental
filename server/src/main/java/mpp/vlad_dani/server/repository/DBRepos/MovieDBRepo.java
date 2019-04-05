package mpp.vlad_dani.server.repository.DBRepos;


import mpp.vlad_dani.common.domain.Movie;
import mpp.vlad_dani.common.domain.validator.Validator;
import mpp.vlad_dani.server.repository.DBRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MovieDBRepo extends DBRepo<Integer, Movie> {

    public MovieDBRepo(Validator<Movie> validator) {
        super(validator);
    }

    @Override
    public Optional<Movie> saveInDB(Movie entity) {
        String sql = "insert into \"Movies\"(\"title\", \"year\", \"genre\", \"duration\",\"rating\", id) values (?,?,?,?,?,?)";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setString(1, entity.getTitle());
            statement.setInt(2, entity.getYear());
            statement.setString(3, entity.getGenre());
            statement.setInt(4, entity.getDuration());
            statement.setFloat(5,entity.getRating());
            statement.setInt(6, entity.getId());

            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<Movie> deleteFromDB(Integer id) {
        Optional<Movie> student = this.getFromDB(id);
        String sql = "delete from \"Movies\" where id=?";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setLong(1, id);

            statement.executeUpdate();

            return Optional.empty();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return student;
    }

    @Override
    public Optional<Movie> updateInDB(Movie entity) {
        String sql = "update \"Movies\" set \"title\"=?, \"year\"=?, \"genre\"=? , \"duration\"=?,\"rating\"=?, where id=?";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setString(1, entity.getTitle());
            statement.setInt(2, entity.getYear());
            statement.setString(3, entity.getGenre());
            statement.setInt(4, entity.getDuration());
            statement.setFloat(5,entity.getRating());
            statement.setInt(6, entity.getId());

            statement.executeUpdate();

            return Optional.of(entity);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Movie> getFromDB(Integer id) {
        String sql = "select * from \"Movies\" where id=?";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Movie movie = new Movie(resultSet.getInt("id"),resultSet.getString("Title"),resultSet.getInt("Year"),
                        resultSet.getString("Genre"), resultSet.getInt("Duration"),resultSet.getFloat("Rating"));
                //movie.setId(resultSet.getInt("id"));
                return Optional.of(movie);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Set<Movie> findAllFromDB() {
        Set<Movie> students = new HashSet<>();
        String sql = "select * from \"Movies\"";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
               Integer id = resultSet.getInt("id");
                String title= resultSet.getString("Title");
                Integer year =  resultSet.getInt("Year");
                String genre = resultSet.getString("Genre");
                Integer duration = resultSet.getInt("Duration");
                Float rating = resultSet.getFloat("Rating");

                Movie student = new Movie(id,title,year,genre,duration,rating);
                student.setId(id);
                students.add(student);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return students;
    }
}
