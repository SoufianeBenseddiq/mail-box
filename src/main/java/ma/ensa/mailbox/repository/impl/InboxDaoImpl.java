package ma.ensa.mailbox.repository.impl;

import ma.ensa.mailbox.config.database.MySQLConnection;
import ma.ensa.mailbox.model.Inbox;
import ma.ensa.mailbox.repository.facade.InboxDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InboxDaoImpl implements InboxDao {

    @Override
    public int save(Inbox inbox) {
        try{
            MySQLConnection mySQLConnection = new MySQLConnection();
            String sql = "INSERT INTO inbox (subject, sender, body) VALUES (?,?,?)";
            PreparedStatement ps = mySQLConnection.getConnection().prepareStatement(sql);
            ps.setString(1, inbox.getSubject());
            ps.setString(2, inbox.getSender());
            ps.setString(3, inbox.getBody());
            int i = ps.executeUpdate();
            mySQLConnection.getConnection().close();
            return i;
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Inbox> findAll() {
        return List.of();
    }

    @Override
    public int delete(Inbox inbox) {
        String sql = "DELETE FROM inbox WHERE sender = ? AND subject = ? AND body = ?";
        try {
            MySQLConnection mySQLConnection = new MySQLConnection();
            PreparedStatement stmt = mySQLConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, inbox.getSender());
            stmt.setString(2, inbox.getSubject());
            stmt.setString(3, inbox.getBody());
            return stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void deleteAllMails() {
        String sql = "DELETE FROM inbox";
        try  {
            MySQLConnection mySQLConnection = new MySQLConnection();
            PreparedStatement stmt = mySQLConnection.getConnection().prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
