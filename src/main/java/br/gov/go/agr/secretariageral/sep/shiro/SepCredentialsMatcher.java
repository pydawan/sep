package br.gov.go.agr.secretariageral.sep.shiro;

import java.util.logging.Logger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 * @author thiago
 * @version v1.0.0 23/01/2017
 * @since v1.0.0
 */
public class SepCredentialsMatcher extends HashedCredentialsMatcher {
   
   private static final String MSG_VERIFICANDO_CREDENCIAIS = "[CREDENCIAIS] => VERIFICANDO AS CREDENCIAIS DO USUÁRIO: %s";
   private static final Logger logger = Logger.getLogger(SepCredentialsMatcher.class.getName());
   
   public SepCredentialsMatcher() {
      super();
      setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
      setHashIterations(1024);
      setStoredCredentialsHexEncoded(false);
   }
   
   @Override
   public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
      logger.info(String.format(MSG_VERIFICANDO_CREDENCIAIS, token.getPrincipal()));
      return super.doCredentialsMatch(token, info);
   }
   
}
