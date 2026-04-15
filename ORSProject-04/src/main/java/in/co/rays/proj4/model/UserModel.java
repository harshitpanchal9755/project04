package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.EmailBuilder;
import in.co.rays.proj4.util.EmailMessage;
import in.co.rays.proj4.util.EmailUtility;
import in.co.rays.proj4.util.JdbcDataSource;

/**
 * UserModel is a Model class that handles all database operations for the User
 * entity. It is used to perform CRUD operations on the ST_USER table.
 *
 * This class contains methods for adding, updating, deleting, searching, and
 * authenticating users, along with password management and user registration
 * functionality.
 *
 * It interacts with the database using JDBC and communicates with the
 * {@link UserBean} for data transfer.
 *
 * @author Harshit Panchal
 */
public class UserModel {

    /** Logger instance for logging debug, info, warn, error and fatal messages */
    Logger log = Logger.getLogger(UserModel.class);

    /**
     * Returns the next available primary key for the ST_USER table by
     * fetching the current maximum ID and incrementing it by 1.
     *
     * @return next available primary key as Integer
     * @throws DataBaseException if any database error occurs while fetching the key
     */
    public Integer nextpk() throws DataBaseException {
        log.debug("nextpk is called");

        Connection conn = null;
        log.warn("Duplicate login found");
        int pk = 0;

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("select max(id) from st_user");
            ResultSet rs = ps.executeQuery();

            log.debug("Preparing insert query");

            while (rs.next()) {
                pk = rs.getInt(1);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            throw new DataBaseException("Exception : exception is getting pk");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    /**
     * Adds a new User record to the ST_USER table. Before inserting, it checks
     * if a user with the same login already exists to prevent duplicates.
     * Rolls back the transaction if any error occurs during insertion.
     *
     * @param bean the UserBean containing the user details to be added
     * @return the primary key of the newly added user record
     * @throws DuplicateException if a user with the same login already exists
     * @throws ApplicationException if any error occurs during the add operation
     */
    public long add(UserBean bean) throws DuplicateException, ApplicationException {
        log.debug("add is called");

        Connection conn = null;
        int pk = 0;

        UserBean beanexist = findByLogin(bean.getLogin());
        if (beanexist != null) {
            log.warn("duplicate login found");
            throw new DuplicateException("Login already exist");
        }

        try {
            pk = nextpk();
            conn = JdbcDataSource.getConnection();
            conn.setAutoCommit(false);
            log.debug("prepared insert query");
            PreparedStatement ps = conn.prepareStatement("insert into st_user values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, pk);
            ps.setString(2, bean.getFirstName());
            ps.setString(3, bean.getLastName());
            ps.setString(4, bean.getLogin());
            ps.setString(5, bean.getPassword());
            ps.setDate(6, new java.sql.Date(bean.getDob().getTime()));
            ps.setString(7, bean.getMobileNo());
            ps.setLong(8, bean.getRoleId());
            ps.setString(9, bean.getGender());
            ps.setString(10, bean.getCreatedBy());
            ps.setString(11, bean.getModifiedBy());
            ps.setTimestamp(12, bean.getCreatedDateTime());
            ps.setTimestamp(13, bean.getModifiedDateTime());
            ps.executeUpdate();
            conn.commit();
            log.info("User add is Successfull");
            ps.close();
        } catch (Exception e) {
            log.error("error is add");
            try {
                conn.rollback();
            } catch (Exception ex) {
                log.fatal("Rollaback is fail");
                throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in add User");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates an existing User record in the ST_USER table. Before updating,
     * it checks if another user with the same login already exists to prevent
     * duplicates. Rolls back the transaction if any error occurs during update.
     *
     * @param bean the UserBean containing the updated user details
     * @throws DuplicateException if another user with the same login already exists
     * @throws ApplicationException if any error occurs during the update operation
     */
    public void update(UserBean bean) throws DuplicateException, ApplicationException {
        log.debug("delete is start()");

        Connection conn = null;

        UserBean beanExist = findByLogin(bean.getLogin());

        if (beanExist != null && beanExist.getId() != bean.getId()) {
            log.warn("duplicate login i during");
            throw new DuplicateException("Login already exist");
        }

        try {
            conn = JdbcDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_user set first_name = ?, last_name = ?, login = ?, password = ?, dob = ?, mobile_no = ?, role_id = ?, gender = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
            pstmt.setString(1, bean.getFirstName());
            pstmt.setString(2, bean.getLastName());
            pstmt.setString(3, bean.getLogin());
            pstmt.setString(4, bean.getPassword());
            pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setString(6, bean.getMobileNo());
            pstmt.setLong(7, bean.getRoleId());
            pstmt.setString(8, bean.getGender());
            pstmt.setString(9, bean.getCreatedBy());
            pstmt.setString(10, bean.getModifiedBy());
            pstmt.setTimestamp(11, bean.getCreatedDateTime());
            pstmt.setTimestamp(12, bean.getModifiedDateTime());
            pstmt.setLong(13, bean.getId());
            pstmt.executeUpdate();
            conn.commit();
            log.info("update is successgull");
            pstmt.close();
        } catch (Exception e) {
            log.error("Error is update");
            try {
                conn.rollback();
            } catch (Exception ex) {
                log.fatal("Rollaback is fail");
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating User ");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes an existing User record from the ST_USER table based on the
     * ID present in the provided UserBean.
     *
     * @param bean the UserBean containing the ID of the user to be deleted
     * @throws ApplicationException if any error occurs during the delete operation
     */
    public void delete(UserBean bean) throws ApplicationException {
        log.debug("delete Started");

        Connection conn = null;

        try {
            conn = JdbcDataSource.getConnection();
            log.warn("prepared insert query");
            PreparedStatement pstmt = conn.prepareStatement("delete from st_user where id = ?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            log.error("error is delete");
            e.printStackTrace();
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds and returns a User record from the ST_USER table based on the
     * given primary key.
     *
     * @param pk the primary key of the user to be fetched
     * @return UserBean containing the user details, or null if not found
     * @throws ApplicationException if any error occurs while fetching the record
     */
    public UserBean findByPk(long pk) throws ApplicationException {
        log.debug("findBypk is started");

        UserBean bean = null;
        Connection conn = null;

        StringBuffer sql = new StringBuffer("select * from st_user where id = ?");

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new UserBean();
                bean.setId(rs.getLong(1));
                bean.setFirstName(rs.getString(2));
                bean.setLastName(rs.getString(3));
                bean.setLogin(rs.getString(4));
                bean.setPassword(rs.getString(5));
                bean.setDob(rs.getDate(6));
                bean.setMobileNo(rs.getString(7));
                bean.setRoleId(rs.getLong(8));
                bean.setGender(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDateTime(rs.getTimestamp(12));
                bean.setModifiedDateTime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            log.error("error is findbypk");
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in getting User by pk");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds and returns a User record from the ST_USER table based on the
     * given login ID.
     *
     * @param login the login ID (email/username) of the user to be fetched
     * @return UserBean containing the user details, or null if not found
     * @throws ApplicationException if any error occurs while fetching the record
     */
    public UserBean findByLogin(String login) throws ApplicationException {
        log.debug("findbylogin is started");

        StringBuffer sql = new StringBuffer("select * from st_user where login = ?");

        UserBean bean = null;
        Connection conn = null;

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new UserBean();
                bean.setId(rs.getLong(1));
                bean.setFirstName(rs.getString(2));
                bean.setLastName(rs.getString(3));
                bean.setLogin(rs.getString(4));
                bean.setPassword(rs.getString(5));
                bean.setDob(rs.getDate(6));
                bean.setMobileNo(rs.getString(7));
                bean.setRoleId(rs.getLong(8));
                bean.setGender(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDateTime(rs.getTimestamp(12));
                bean.setModifiedDateTime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            log.error("error is findbylogin");
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in getting User by login");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Authenticates a user by verifying the provided login ID and password
     * against the ST_USER table records.
     *
     * @param login    the login ID (email/username) of the user
     * @param password the password of the user
     * @return UserBean containing the authenticated user details, or null if
     *         authentication fails
     * @throws ApplicationException if any error occurs during authentication
     */
    public UserBean authenticate(String login, String password) throws ApplicationException {
        log.debug("authenticate() called");

        UserBean bean = null;
        Connection conn = null;

        StringBuffer sql = new StringBuffer("select * from st_user where login = ? and password = ?");

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, login);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new UserBean();
                bean.setId(rs.getLong(1));
                bean.setFirstName(rs.getString(2));
                bean.setLastName(rs.getString(3));
                bean.setLogin(rs.getString(4));
                bean.setPassword(rs.getString(5));
                bean.setDob(rs.getDate(6));
                bean.setMobileNo(rs.getString(7));
                bean.setRoleId(rs.getLong(8));
                bean.setGender(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDateTime(rs.getTimestamp(12));
                bean.setModifiedDateTime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in get roles");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Changes the password of an existing user after validating the old password.
     * On successful password change, sends a confirmation email to the user's
     * registered login ID.
     *
     * @param id          the unique ID of the user whose password is to be changed
     * @param oldPassword the current password of the user for validation
     * @param newPassword the new password to be set for the user
     * @return true if the password was changed successfully, false otherwise
     * @throws RecordNotFoundException if the old password does not match the
     *                                 existing password
     * @throws ApplicationException    if any error occurs during the password
     *                                 change operation
     */
    public boolean changePassword(Long id, String oldPassword, String newPassword)
            throws RecordNotFoundException, ApplicationException {

        boolean flag = false;

        UserBean beanExist = findByPk(id);

        if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
            beanExist.setPassword(newPassword);
            try {
                update(beanExist);
                flag = true;
            } catch (DuplicateException e) {
                throw new ApplicationException("Login Id already exist");
            }
        } else {
            throw new RecordNotFoundException("Old Password is Invalid");
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("login", beanExist.getLogin());
        map.put("password", beanExist.getPassword());
        map.put("firstName", beanExist.getFirstName());
        map.put("lastName", beanExist.getLastName());

        String message = EmailBuilder.getChangePasswordMessage(map);

        EmailMessage msg = new EmailMessage();
        msg.setTo(beanExist.getLogin());
        msg.setSubject("ORSProject-04 Password has been changed Successfully.");
        msg.setMessage(message);
        msg.setMessageType(EmailMessage.HTML_MSG);

        EmailUtility.sendMail(msg);

        return flag;
    }

    /**
     * Sends the existing password to the user's registered login ID (email)
     * in case the user has forgotten their password. Throws an exception if
     * the provided login ID does not exist in the system.
     *
     * @param login the login ID (email) of the user who forgot the password
     * @return true if the forget password email was sent successfully
     * @throws RecordNotFoundException if no user is found with the given login ID
     * @throws ApplicationException    if any error occurs while sending the email
     */
    public boolean forgetPassword(String login) throws RecordNotFoundException, ApplicationException {

        UserBean userData = findByLogin(login);
        boolean flag = false;

        if (userData == null) {
            throw new RecordNotFoundException("Email ID does not exists..!!");
        }

        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("login", userData.getLogin());
            map.put("password", userData.getPassword());
            map.put("firstName", userData.getFirstName());
            map.put("lastName", userData.getLastName());

            String message = EmailBuilder.getForgetPasswordMessage(map);

            EmailMessage msg = new EmailMessage();
            msg.setTo(login);
            msg.setSubject("ORSProject-04 Password Reset");
            msg.setMessage(message);
            msg.setMessageType(EmailMessage.HTML_MSG);

            EmailUtility.sendMail(msg);
            flag = true;
        } catch (Exception e) {
            throw new ApplicationException("Please check your internet connection..!!");
        }
        return flag;
    }

    /**
     * Returns a list of all User records from the ST_USER table without any
     * filters or pagination.
     *
     * @return List of UserBean containing all user records
     * @throws ApplicationException if any error occurs while fetching the records
     */
    public List list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Searches for User records in the ST_USER table based on the provided
     * search criteria with optional pagination support. If the bean is null,
     * all records are returned. Supports filtering by id, first name, last name,
     * login, password, date of birth, mobile number, role ID, and gender.
     *
     * @param bean     the UserBean containing the search criteria, or null to
     *                 fetch all records
     * @param pageNo   the page number for pagination (use 0 for no pagination)
     * @param pageSize the number of records per page (use 0 for no pagination)
     * @return List of UserBean matching the search criteria
     * @throws ApplicationException if any error occurs during the search operation
     */
    public List<UserBean> search(UserBean bean, int pageNo, int pageSize) throws ApplicationException {

        Connection conn = null;
        ArrayList<UserBean> list = new ArrayList<UserBean>();

        StringBuffer sql = new StringBuffer("select * from st_user where 1=1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }
            if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
                sql.append(" and first_name like '" + bean.getFirstName() + "%'");
            }
            if (bean.getLastName() != null && bean.getLastName().length() > 0) {
                sql.append(" and last_name like '" + bean.getLastName() + "%'");
            }
            if (bean.getLogin() != null && bean.getLogin().length() > 0) {
                sql.append(" and login like '" + bean.getLogin() + "%'");
            }
            if (bean.getPassword() != null && bean.getPassword().length() > 0) {
                sql.append(" and password like '" + bean.getPassword() + "%'");
            }
            if (bean.getDob() != null && bean.getDob().getDate() > 0) {
                sql.append(" and dob = " + bean.getDob());
            }
            if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
                sql.append(" and mobile_no = " + bean.getMobileNo());
            }
            if (bean.getRoleId() > 0) {
                sql.append(" and role_id = " + bean.getRoleId());
            }
            if (bean.getGender() != null && bean.getGender().length() > 0) {
                sql.append(" and gender like '" + bean.getGender() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new UserBean();
                bean.setId(rs.getLong(1));
                bean.setFirstName(rs.getString(2));
                bean.setLastName(rs.getString(3));
                bean.setLogin(rs.getString(4));
                bean.setPassword(rs.getString(5));
                bean.setDob(rs.getDate(6));
                bean.setMobileNo(rs.getString(7));
                bean.setRoleId(rs.getLong(8));
                bean.setGender(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDateTime(rs.getTimestamp(12));
                bean.setModifiedDateTime(rs.getTimestamp(13));
                list.add(bean);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in search user");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return list;
    }

    /**
     * Registers a new user by adding their details to the ST_USER table and
     * sends a registration confirmation email to the user's login ID upon
     * successful registration.
     *
     * @param bean the UserBean containing the new user's registration details
     * @return the primary key of the newly registered user record
     * @throws DuplicateException   if a user with the same login already exists
     * @throws ApplicationException if any error occurs during registration or
     *                              while sending the confirmation email
     */
    public long registerUser(UserBean bean) throws DuplicateException, ApplicationException {

        long pk = add(bean);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("login", bean.getLogin());
        map.put("password", bean.getPassword());

        String message = EmailBuilder.getUserRegistrationMessage(map);

        EmailMessage msg = new EmailMessage();
        msg.setTo(bean.getLogin());
        msg.setSubject("Registration is successful for ORSProject-04");
        msg.setMessage(message);
        msg.setMessageType(EmailMessage.HTML_MSG);

        EmailUtility.sendMail(msg);

        return pk;
    }
}