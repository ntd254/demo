package com.example.demo.repository;

import com.example.demo.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {

    List<Contact> findContactsByStatus(String status);
}

/* Use for Spring jdbc
public class ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    public ContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveContactMsg(Contact contact) {
        String sql = "insert into contact_msg(name, mobile_num, email, subject, message, status, " +
                "created_at, created_by) values (?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, contact.getName(), contact.getMobileNum(), contact.getEmail(), contact.getSubject(),
                contact.getMessage(), contact.getStatus(), contact.getCreateAt(), contact.getCreatedBy());
    }

    public List<Contact> findMsgsWithStatus(String status) {
        String sql = "select * from contact_msg where status = ?";
        return jdbcTemplate.query(sql, (PreparedStatement preparedStatement) -> {
            preparedStatement.setString(1, status);
        }, (ResultSet rs, int rowNum) -> {
            Contact contact = new Contact();
            contact.setContactId(rs.getInt("contact_id"));
            contact.setName(rs.getString("name"));
            contact.setMobileNum(rs.getString("mobile_num"));
            contact.setEmail(rs.getString("email"));
            contact.setSubject(rs.getString("subject"));
            contact.setMessage(rs.getString("message"));
            contact.setStatus(rs.getString("status"));
            contact.setCreateAt(rs.getTimestamp("created_at").toLocalDateTime());
            contact.setCreatedBy(rs.getString("created_by"));

            if (rs.getTimestamp("updated_at") != null) {
                contact.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
            contact.setUpdatedBy(rs.getString("updated_by"));
            return contact;
        });
    }

    public int updateMsgStatus(int contactId, String status, String updatedBy) {
        String sql = "update contact_msg set status = ?, updated_by = ?, updated_at = ? where contact_id = ?";
        return jdbcTemplate.update(sql, (PreparedStatement preparedStatement) -> {
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, updatedBy);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(4, contactId);
        });
    }
} */
