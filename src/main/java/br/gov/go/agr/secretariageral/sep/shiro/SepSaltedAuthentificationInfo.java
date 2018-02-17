package br.gov.go.agr.secretariageral.sep.shiro;

import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

/**
 * Representa as informações de autenticação
 * processadas por uma função hash criptográfica com uso de salt.
 * 
 * @author thiago
 * @version v1.0.0 23/01/2017
 * @since v1.0.0
 */
public class SepSaltedAuthentificationInfo implements SaltedAuthenticationInfo {
   
   private static final long serialVersionUID = 1L;
   private final String username;
   private final String password;
   private final String salt;
   
   public SepSaltedAuthentificationInfo(String username, String password, String salt) {
      this.username = username;
      this.password = password;
      this.salt = salt;
   }
   
   @Override
   public PrincipalCollection getPrincipals() {
      PrincipalCollection coll = new SimplePrincipalCollection(username, username);
      return coll;
   }
   
   @Override
   public Object getCredentials() {
      return password;
   }
   
   @Override
   public ByteSource getCredentialsSalt() {
      return new SimpleByteSource(salt);
   }
   
}
