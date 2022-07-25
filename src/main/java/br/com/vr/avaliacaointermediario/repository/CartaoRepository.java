package br.com.vr.avaliacaointermediario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vr.avaliacaointermediario.model.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, String> {
}
