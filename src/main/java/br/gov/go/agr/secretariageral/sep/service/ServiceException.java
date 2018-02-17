package br.gov.go.agr.secretariageral.sep.service;

/**
 * @author thiago
 * @version v1.0.0 24/02/2017
 * @since v1.0.0
 */
public class ServiceException extends RuntimeException {
   
   private static final long serialVersionUID = 1L;
   
   public ServiceException() {
      
   }
   
   public ServiceException(String message) {
      super(message);
   }
   
   public ServiceException(Throwable cause) {
      super(cause);
   }
   
   public ServiceException(String message, Throwable cause) {
      super(message, cause);
   }
   
}
