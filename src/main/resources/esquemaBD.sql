DROP SCHEMA IF EXISTS `avaliacao_intermediario` ;

CREATE SCHEMA IF NOT EXISTS `avaliacao_intermediario` DEFAULT CHARACTER SET utf8 ;
USE `avaliacao_intermediario` ;

CREATE TABLE IF NOT EXISTS `cartoes` (
  `numero` VARCHAR(16) NOT NULL,
  `senha` VARCHAR(32) NOT NULL,
  `saldo` DECIMAL(65, 2) NOT NULL,
  PRIMARY KEY (`numero`)
);

CREATE TABLE IF NOT EXISTS `transacoes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `valor` DECIMAL(2) NOT NULL,
  `data_hora` DATETIME NOT NULL,
  `numero_cartao` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_transacoes_cartoes_idx` (`numero_cartao` ASC) VISIBLE,
  CONSTRAINT `fk_transacoes_cartoes`
    FOREIGN KEY (`numero_cartao`)
    REFERENCES `avaliacao_intermediario`.`cartoes` (`numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
