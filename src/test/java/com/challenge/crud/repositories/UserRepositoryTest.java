package com.challenge.crud.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.crud.entities.UserEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * FindUserByEmailAndName
	 */
	@Test
	public void deveCarregarUmaPaginadeUsuariosAoBuscarSemFiltro() {	
		String name = null;
		String email = null;
		Pageable pageable = PageRequest.of(0, 10);
		Page<UserEntity> user = userRepository.findUserByEmailAndName(email, name, pageable);
		
		Assert.assertNotNull(user);	
		Assert.assertTrue(user.getSize() > 0);	
	}
	
	@Test
	public void deveCarregarUmaPaginadeUsuariosComOsNomesCorrespondendoAoFiltroPorNome() {	
		String name = "Maria das Testes";
		String email = null;
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<UserEntity> user = userRepository.findUserByEmailAndName(email, name, pageable);
		
		Assert.assertNotNull(user);	
		Assert.assertTrue(user.getContent().get(0).getName().contains(name));
	}
	
	@Test
	public void deveCarregarUmaPaginaComUmUsuarioAoFiltrarPorEmailJaCadastradoNaBase() {	
		String name = null;
		String email = "existe@email.com";	
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<UserEntity> user = userRepository.findUserByEmailAndName(email, name, pageable);
		
		Assert.assertNotNull(user);	
		Assert.assertEquals(email, user.getContent().get(0).getEmail());
	}
	
	@Test
	public void deveCarregarUmaPaginaVaziaAoFiltrarPorEmailNaoCadastradoNaBase() {	
		String name = null;
		String email = "naoexiste@email.com";	
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<UserEntity> user = userRepository.findUserByEmailAndName(email, name, pageable);
		
		Assert.assertTrue(user.getContent().size() == 0);
	}
	
	
	@Test
	public void deveCarregarUmUsuarioAoBuscarPeloSeuNome() {
		String nomeUsuario = "Jose das Testes";
		List<UserEntity> usuario = userRepository.findByName(nomeUsuario);
		
		Assert.assertEquals(nomeUsuario, usuario.get(0).getName());
	}
	
	@Test
	public void naoDeveCarregarUmUsuarioCujoOnomeNaoEstejaCadastrado() {
		String nomeUsuario = "Nome não esistente";
		List<UserEntity> usuario = userRepository.findByName(nomeUsuario);
		
		Assert.assertTrue(usuario.size() == 0);
	}
	
	@Test
	public void deveCarregarUmUsuarioAoBuscarPeloEmailCorreto() {
		String email = "existe@email.com";	
		Optional<UserEntity> user = userRepository.findByEmail(email);
		Assert.assertEquals(email, user.get().getEmail());
	}

	@Test
	public void naoDeveCarregarUmUsuarioCujoEmailNaoEstejaCadastrado() {
		String email = "naoexiste@email.com";	
		Optional<UserEntity> user = userRepository.findByEmail(email);
		Assert.assertTrue(user.isEmpty());	
	}
	
}

