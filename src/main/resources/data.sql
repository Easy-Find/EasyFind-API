insert into usuario (email,senha,tipo_usuario) values('pedro@gmail.com','123','COMERCIANTE');
insert into endereco (cep, geolocalizacaox, geolocalizacaoy, numero, rua) values('09531080', 154785412512547, 154785412512547, 17, 'Rua helena Mussumeci');
insert into comerciante(cnpj, nome, razao_social, fk_endereco, fk_usuario) values(15478541251254,'Pedro', 'jau', 1,1);
insert into estabelecimento(email_contato, enquadramento_juridico, is_ativo, nome, segmento, fk_comerciante, fk_endereco)values('box@gmail.com', 'MEI', true, 'Box delivery', 'roupa', 1,1);
insert into secao(descricao, fk_estabelecimento) values('carregador', 1);
insert into secao(descricao, fk_estabelecimento) values('outros', 1);
insert into tag(descricao)values('teste');
insert into produto( preco, fk_secao, nome, is_ativo, is_promocao_ativa, preco_oferta,categoria, codigo_barras, codigo_sku, descricao)values(10.0, 2,'Power Bank', true,false,7.5,'categoria teste','123456cb','codigoskuteste','descricaoteste');