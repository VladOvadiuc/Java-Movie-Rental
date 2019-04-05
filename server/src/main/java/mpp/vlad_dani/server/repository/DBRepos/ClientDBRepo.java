package mpp.vlad_dani.server.repository.DBRepos;

import mpp.vlad_dani.common.domain.Client;
import mpp.vlad_dani.common.domain.validator.Validator;
import mpp.vlad_dani.server.repository.DBRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ClientDBRepo extends DBRepo<Integer, Client> {

    public ClientDBRepo(Validator<Client> validator) {
        super(validator);
    }

    @Override
    public Optional<Client> saveInDB(Client entity) {
        String sql = "insert into \"Clients\"(\"name\", id) values (?,?)";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getId());

            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<Client> deleteFromDB(Integer id) {
        Optional<Client> student = this.getFromDB(id);
        String sql = "delete from \"Clients\" where id=?";
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
    public Optional<Client> updateInDB(Client entity) {
        String sql = "update \"Clients\" set \"name\"=? where id=?";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getId());

            statement.executeUpdate();

            return Optional.of(entity);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> getFromDB(Integer id) {
        String sql = "select * from \"Clients\" where id=?";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Client movie = new Client(resultSet.getInt("id"),resultSet.getString("name"));
                //movie.setId(resultSet.getInt("id"));
                return Optional.of(movie);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Set<Client> findAllFromDB() {
        Set<Client> students = new HashSet<>();
        String sql = "select * from \"Clients\"";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()
        )
        {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name= resultSet.getString("name");

                Client student = new Client(id,name);
                student.setId(id);
                students.add(student);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return students;
    }
}
