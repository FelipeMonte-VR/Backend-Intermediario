package br.com.vr.avaliacaointermediario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vr.avaliacaointermediario.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long>{
}
