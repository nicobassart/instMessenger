package ar.com.instMessenger.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ar.com.instMessenger.entity.Usuario;
import ar.com.instMessenger.servicios.dao.IUsuariosDao;

public class UserDetailsService implements
		org.springframework.security.core.userdetails.UserDetailsService {
	
	@Autowired
	IUsuariosDao usuariosDao;
	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		Usuario usuario = usuariosDao.getUsuario(username);

		if (usuario == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(username, usuario.getPassword(), true, true, true, true,AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
	}

}