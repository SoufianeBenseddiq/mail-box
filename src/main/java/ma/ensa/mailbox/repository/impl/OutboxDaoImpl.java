package ma.ensa.mailbox.repository.impl;

import ma.ensa.mailbox.config.database.MySQLConnection;
import ma.ensa.mailbox.model.Outbox;
import ma.ensa.mailbox.repository.facade.OutboxDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OutboxDaoImpl implements OutboxDao {

    @Override
    public int sendMail(Outbox outbox) {
        try{
            MySQLConnection mySQLConnection = new MySQLConnection();
            String sql = "INSERT INTO outbox (subject, receiver, body) VALUES (?,?,?)";
            PreparedStatement ps = mySQLConnection.getConnection().prepareStatement(sql);
            ps.setString(1, outbox.getSubject());
            ps.setString(2, outbox.getReceiver());
            ps.setString(3, outbox.getBody());
            int i = ps.executeUpdate();
            mySQLConnection.getConnection().close();
            return i;
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            return -1;
        }
    }




    @Override
    public void deleteAllMails() {
        String sql = "DELETE FROM outbox";
        try  {
            MySQLConnection mySQLConnection = new MySQLConnection();
            PreparedStatement stmt = mySQLConnection.getConnection().prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
