package mpp.vlad_dani.server.repository.DBRepos;

import mpp.vlad_dani.common.domain.Rental;
import mpp.vlad_dani.common.domain.validator.Validator;
import mpp.vlad_dani.server.repository.DBRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RentalDBRepo extends DBRepo<Integer, Rental> {

    public RentalDBRepo(Validator<Rental> validator) {
        super(validator);
    }


    @Override
    public Optional<Rental> saveInDB(Rental entity) {
        String sql = "insert into \"Rentals\"(\"movieid\",\"clientid\", id) values (?,?,?)";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setInt(1, entity.getMovie());
            statement.setInt(2, entity.getClient());
            statement.setInt(3, entity.getId());

            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<Rental> deleteFromDB(Integer id) {
        Optional<Rental> student = this.getFromDB(id);
        String sql = "delete from \"Rentals\" where id=?";
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
    public Optional<Rental> updateInDB(Rental entity) {
        String sql = "update \"Rentals\" set \"movieid\"=?,\"clientid\"=? where id=?";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setInt(1, entity.getMovie());
            statement.setInt(2, entity.getClient());
            statement.setInt(3, entity.getId());

            statement.executeUpdate();

            return Optional.of(entity);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Rental> getFromDB(Integer id) {
        String sql = "select * from \"Rentals\" where id=?";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Rental movie = new Rental(resultSet.getInt("MovieId"),resultSet.getInt("ClientId"),resultSet.getInt("id"));
                //movie.setId(resultSet.getInt("id"));
                return Optional.of(movie);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Set<Rental> findAllFromDB() {
        Set<Rental> students = new HashSet<>();
        String sql = "select * from \"Rentals\"";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()
        )
        {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer mid = resultSet.getInt("movieId");
                Integer cid = resultSet.getInt("clientId");

                Rental student = new Rental(mid,cid,id);
                student.setId(id);
                students.add(student);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return students;
    }
}
