package main.by.htp.ts.dao;

public class DAOException extends Exception {

    private static final long serialVersionUID = -8346004603337016784L;
    public DAOException(String message, Exception e) {
        super(message, e);
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Exception e) {
        super(e);
    }
}
