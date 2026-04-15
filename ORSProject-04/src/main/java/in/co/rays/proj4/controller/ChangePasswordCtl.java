package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * ChangePasswordCtl is a Controller class that handles the Change Password
 * functionality for logged-in users.
 *
 * It is mapped to the URL pattern /ChangePasswordCtl and extends BaseCtl
 * to inherit common controller behavior such as validation dispatching
 * and audit field population.
 *
 * This controller validates the old password, new password strength, and
 * confirm password match before delegating the password change to UserModel.
 * It also supports navigation to the My Profile page via the
 * OP_CHANGE_MY_PROFILE operation.
 *
 * @author Harshit Panchal
 */
@WebServlet(name = "ChangePasswordCtl", urlPatterns = {"/ctl/ChangePasswordCtl"})
public class ChangePasswordCtl extends BaseCtl {

    /** Operation constant for navigating to the Change My Profile page */
    public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";

    /**
     * Validates the Change Password form fields from the incoming HTTP request.
     *
     * Skips validation if the operation is OP_CHANGE_MY_PROFILE. Otherwise,
     * validates the following rules and sets error attributes on the request
     * if any rule fails:
     * - Old Password must not be null or empty.
     * - New Password must not be the same as Old Password.
     * - New Password must not be null or empty.
     * - New Password must be between 8 to 12 characters in length.
     * - New Password must contain uppercase, lowercase, digit, and special character.
     * - Confirm Password must not be null or empty.
     * - New Password and Confirm Password must match.
     *
     * @param request the HttpServletRequest containing the password form parameters
     * @return true if all validations pass, false if any validation fails
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;

        String op = request.getParameter("operation");

        if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("oldPassword"))) {
            request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Old Password"));
            pass = false;
        } else if (request.getParameter("oldPassword").equals(request.getParameter("newPassword"))) {
            request.setAttribute("newPassword", "Old and New passwords should be different");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("newPassword"))) {
            request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password"));
            pass = false;
        } else if (!DataValidator.isPasswordLength(request.getParameter("newPassword"))) {
            request.setAttribute("newPassword", "Password should be 8 to 12 characters");
            pass = false;
        } else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
            request.setAttribute("newPassword", "Must contain uppercase, lowercase, digit & special character");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
            request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
            pass = false;
        }

        if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))
                && !"".equals(request.getParameter("confirmPassword"))) {
            request.setAttribute("confirmPassword", "New and confirm passwords not matched");
            pass = false;
        }

        return pass;
    }

    /**
     * Populates and returns a UserBean from the incoming HTTP request parameters.
     *
     * Extracts the oldPassword and confirmPassword fields from the request
     * and sets them into a new UserBean. Also populates audit fields by
     * delegating to populateDTO().
     *
     * @param request the HttpServletRequest containing the password form parameters
     * @return a UserBean populated with oldPassword, confirmPassword, and audit fields
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        UserBean bean = new UserBean();

        bean.setPassword(DataUtility.getString(request.getParameter("oldPassword")));
        bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles HTTP GET requests for the Change Password page.
     *
     * Forwards the request directly to the Change Password JSP view.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during forwarding
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles HTTP POST requests for the Change Password page.
     *
     * Reads the operation parameter and processes accordingly:
     * - OP_SAVE: Retrieves the logged-in user from the session and attempts
     *   to change the password via UserModel.changePassword(). On success,
     *   refreshes the session user bean and sets a success message. On failure
     *   due to an invalid old password, sets an error message. On application
     *   exception, delegates to ServletUtility.handleException().
     * - OP_CHANGE_MY_PROFILE: Redirects the user to the My Profile controller.
     *
     * Forwards back to the Change Password view after processing.
     *
     * @param request  the HttpServletRequest containing the operation and form data
     * @param response the HttpServletResponse used for forwarding or redirecting
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during forwarding or redirecting
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        String newPassword = (String) request.getParameter("newPassword");

        UserBean bean = (UserBean) populateBean(request);
        UserModel model = new UserModel();

        HttpSession session = request.getSession(true);
        UserBean user = (UserBean) session.getAttribute("user");
        long id = user.getId();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            try {
                boolean flag = model.changePassword(id, bean.getPassword(), newPassword);
                if (flag == true) {
                    bean = model.findByLogin(user.getLogin());
                    session.setAttribute("user", bean);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage("Password has been changed Successfully", request);
                }
            } catch (RecordNotFoundException e) {
                ServletUtility.setErrorMessage("Old Password is Invalid", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        } else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
            return;
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the JSP view path for the Change Password page.
     *
     * @return the Change Password JSP view path defined in ORSView
     */
    @Override
    protected String getView() {
        return ORSView.CHANGE_PASSWORD_VIEW;
    }
}