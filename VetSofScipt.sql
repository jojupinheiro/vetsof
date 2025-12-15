create  schema if not exists vetsof default character set utf8;
use vetsof;

CREATE TABLE IF NOT EXISTS usuario (
    pk_idusuario INT NOT NULL AUTO_INCREMENT,
    nome_usuario VARCHAR(50) NOT NULL,
    senha_usuario VARCHAR(8) NOT NULL,
    email_usuario VARCHAR(50) NOT NULL,
    cargo_usuario VARCHAR(50),
    login_usuario VARCHAR(40) NOT NULL,
    tipo_usuario BOOLEAN NOT NULL,
    PRIMARY KEY (pk_idusuario)
);
    
CREATE TABLE IF NOT EXISTS municipio (
    pk_idmunicipio INT NOT NULL AUTO_INCREMENT,
    nome_municipio VARCHAR(60) NOT NULL,
    estado_municipio CHAR(2) NOT NULL,
    PRIMARY KEY (pk_idmunicipio)
);

CREATE TABLE IF NOT EXISTS bairro (
    pk_idbairro INT NOT NULL AUTO_INCREMENT,
    fk_idmunicipio_bairro INT NOT NULL,
    nome_bairro VARCHAR(60) NOT NULL,
    PRIMARY KEY (pk_idbairro),
    FOREIGN KEY (fk_idmunicipio_bairro)
        REFERENCES municipio (pk_idmunicipio)
);    
    
CREATE TABLE IF NOT EXISTS veterinario (
    pk_idveterinario INT NOT NULL AUTO_INCREMENT,
    fk_idbairro_veterinario INT NOT NULL,
    fk_idmunicipio_veterinario INT NOT NULL,
    nome_veterinario VARCHAR(80) NOT NULL,
    sexo_veterinario BOOLEAN NOT NULL,
    cpf_veterinario CHAR(11) NOT NULL,
    crmv_veterinario VARCHAR(5) NOT NULL,
    email_veterinario VARCHAR(50),
    telefone_veterinario VARCHAR(15),
    cep_veterinario CHAR(8),
    rua_veterinario VARCHAR(60),
    numero_veterinario VARCHAR(30),
    observacao_veterinario VARCHAR(500),
    PRIMARY KEY (pk_idveterinario),
    FOREIGN KEY (fk_idmunicipio_veterinario)
        REFERENCES municipio (pk_idmunicipio),
    FOREIGN KEY (fk_idbairro_veterinario)
        REFERENCES bairro (pk_idbairro)
);
    
CREATE TABLE IF NOT EXISTS clinica (
    pk_idclinica INT NOT NULL AUTO_INCREMENT,
    fk_idveterinario_clinica INT,
    fk_idbairro_clinica INT,
    fk_idmunicipio_clinica INT NOT NULL,
    nome_clinica VARCHAR(60) NOT NULL,
    razao_social_clinica VARCHAR(150),
    cnpj_clinica CHAR(14) NOT NULL,
    email_clinica VARCHAR(60) NOT NULL,
    rua_clinica VARCHAR(60),
    numero_clinica VARCHAR(30),
    cep_clinica CHAR(8),
    telefone_clinica VARCHAR(15) NOT NULL,
    telefone_alternativo_clinica VARCHAR(15),
    dtcadastro_clinica DATE,
    observacao_clinica VARCHAR(500),
    PRIMARY KEY (pk_idclinica),
    FOREIGN KEY (fk_idmunicipio_clinica)
        REFERENCES municipio (pk_idmunicipio)
);

CREATE TABLE IF NOT EXISTS clinica_veterinario (
    pk_idclinica_veterinario_cv INT NOT NULL AUTO_INCREMENT,
    fk_idveterinario_cv INT NOT NULL,
    fk_idclinica_cv INT NOT NULL,
    PRIMARY KEY (pk_idclinica_veterinario_cv),
    FOREIGN KEY (fk_idveterinario_cv)
        REFERENCES veterinario (pk_idveterinario),
    FOREIGN KEY (fk_idclinica_cv)
        REFERENCES clinica (pk_idclinica)
);

CREATE TABLE IF NOT EXISTS tutor (
    pk_idtutor INT NOT NULL AUTO_INCREMENT,
    fk_idbairro_tutor INT NOT NULL,
    fk_idmunicipio_tutor INT NOT NULL,
    cpf_tutor VARCHAR(14) NOT NULL,
    nome_tutor VARCHAR(40) NOT NULL,
    sexo_tutor BOOLEAN NOT NULL,
    dtnascimento_tutor DATE,
    rua_tutor VARCHAR(70) NOT NULL,
    numero_tutor VARCHAR(30) NOT NULL,
    cep_tutor CHAR(8) NOT NULL,
    tipo_tutor VARCHAR(50),
    email_tutor VARCHAR(60),
    faixarenda_tutor INT,
    observacao_tutor VARCHAR(500),
    telefone_tutor VARCHAR(15) NOT NULL,
    telefone_alternativo_tutor VARCHAR(15),
    PRIMARY KEY (pk_idtutor),
    FOREIGN KEY (fk_idmunicipio_tutor)
        REFERENCES municipio (pk_idmunicipio),
    FOREIGN KEY (fk_idbairro_tutor)
        REFERENCES bairro (pk_idbairro)
);
    
CREATE TABLE IF NOT EXISTS especie (
    pk_idespecie INT NOT NULL AUTO_INCREMENT,
    nome_especie varchar(60) NOT NULL,
    PRIMARY KEY (pk_idespecie)
);

CREATE TABLE IF NOT EXISTS raca (
    pk_idraca INT NOT NULL AUTO_INCREMENT,
    fk_idespecie INT NOT NULL,
    nome_raca varchar(60) NOT NULL,
    PRIMARY KEY (pk_idraca),
    FOREIGN KEY (fk_idespecie)
        REFERENCES especie (pk_idespecie)
);
    
  CREATE TABLE IF NOT EXISTS pet (
    pk_idpet INT NOT NULL AUTO_INCREMENT,
    fk_idtutor_pet INT NOT NULL,
    fk_idraca_pet INT NOT NULL,
    fk_idespecie_pet INT NOT NULL,
    nome_pet VARCHAR(50) NOT NULL,
    peso_pet DECIMAL(10 , 2 ),
    castrado_pet BOOLEAN NOT NULL,
    vivo_pet BOOLEAN NOT NULL,
    adotado_pet BOOLEAN NOT NULL,
    dtnascimento_pet DATE,
    rfid_pet VARCHAR(20),
    sexo_pet BOOLEAN NOT NULL,
    observacao_pet VARCHAR(500),
    temperamento_pet VARCHAR(150),
    PRIMARY KEY (pk_idPet),
    FOREIGN KEY (fk_idtutor_pet)
        REFERENCES tutor (pk_idtutor),
    FOREIGN KEY (fk_idespecie_pet)
        REFERENCES especie (pk_idespecie),
    FOREIGN KEY (fk_idraca_pet)
        REFERENCES raca (pk_idraca)
);  
    
   CREATE TABLE IF NOT EXISTS atendimento (
    pk_idatendimento INT NOT NULL AUTO_INCREMENT,
    fk_idpet_atendimento INT NOT NULL,
    fk_idclinica_atendimento INT NOT NULL,
    fk_idveterinario_atendimento INT NOT NULL,
    valortotal_atendimento DECIMAL(10 , 2 ) NOT NULL,
    anamnese_atendimento TEXT,
    diagnostico_atendimento VARCHAR(200),
    descricao_atendimento TEXT,
    exame_fisico_atendimento TEXT,
    dt_atendimento DATE NOT NULL,
    horario_atendimento TIME,
    PRIMARY KEY (pk_idatendimento),
    FOREIGN KEY (fk_idpet_atendimento)
        REFERENCES pet (pk_idpet),
    FOREIGN KEY (fk_idclinica_atendimento)
        REFERENCES clinica (pk_idclinica),
    FOREIGN KEY (fk_idveterinario_atendimento)
        REFERENCES veterinario (pk_idveterinario)
); 

CREATE TABLE IF NOT EXISTS servico (
    pk_idservico INT NOT NULL AUTO_INCREMENT,
    nome_servico VARCHAR(60) NOT NULL,
    valor_servico DECIMAL(8 , 2 ),
    descricao_servico VARCHAR(500),
    PRIMARY KEY (pk_idservico)
);

CREATE TABLE IF NOT EXISTS servico_realizado (
    pk_idservico_realizado INT NOT NULL AUTO_INCREMENT,
    fk_idservico_servico_realizado INT NOT NULL,
    fk_idpet_servico_realizado INT NOT NULL,
    idatendimento_servico_realizado INT,
    iddiaria_internacao_servico_realizado INT,
    valor_servico_realizado DECIMAL(8 , 2 ),
    observacao_servico_realizado VARCHAR(500),
    quantidade_servico_realizado INT NOT NULL DEFAULT 1,
    PRIMARY KEY (pk_idservico_realizado),
    FOREIGN KEY (fk_idservico_servico_realizado)
        REFERENCES servico (pk_idservico),
	FOREIGN KEY (fk_idpet_servico_realizado)
        REFERENCES pet (pk_idpet)
);

CREATE TABLE IF NOT EXISTS exame (
    pk_idexame INT NOT NULL AUTO_INCREMENT,
    nome_exame VARCHAR(60) NOT NULL,
    descricao_exame VARCHAR(500),
    valor_exame DECIMAL(8 , 2 ),
    PRIMARY KEY (pk_idexame)
);

CREATE TABLE IF NOT EXISTS exame_realizado (
    pk_idexame_realizado INT NOT NULL AUTO_INCREMENT,
    fk_idexame_exame_realizado INT NOT NULL,
    fk_idpet_exame_realizado INT NOT NULL,
    idatendimento_exame_realizado INT,
    valor_exame_realizado DECIMAL(8 , 2 ),
    observacao_exame_realizado VARCHAR(500),
    resultado_exame_realizado VARCHAR(500),
    PRIMARY KEY (pk_idexame_realizado),
    FOREIGN KEY (fk_idexame_exame_realizado)
        REFERENCES exame (pk_idexame),
	FOREIGN KEY (fk_idpet_exame_realizado)
        REFERENCES pet (pk_idpet)
);

CREATE TABLE IF NOT EXISTS tipo_vac (
	pk_idtipo_vac int not null auto_increment,
    tipo_vac varchar(50) unique not null,				-- Informa a categoria à que a vacina pertence
    PRIMARY KEY(pk_idtipo_vac)
);

CREATE TABLE IF NOT EXISTS nome_vac (
	pk_idnome_vac int not null auto_increment,
    fk_idtipo_vac int not null,
    laboratorio_nome_vac varchar(50),                   -- Informa o laboratório da vacina
    nome_vac varchar(50) unique not null,				-- Informa o nome comercial da vacina
    valor_nome_vac DECIMAL(6,2),
    PRIMARY KEY(pk_idnome_vac)
);

CREATE TABLE IF NOT EXISTS vacina (
    pk_idvacina INT NOT NULL AUTO_INCREMENT,
    fk_idpet_vacina INT NOT NULL,
    fk_idnome_vac_vacina INT NOT NULL,
    fk_idtipo_vac_vacina INT NOT NULL,
    idatendimento_vacina INT,
    dtvacina DATE NOT NULL,
    status_vacina BOOLEAN NOT NULL,             -- Informa se a vacina é aplicada ou programada
    observacao_vacina VARCHAR(500),
    dose_atual_vacina INT,
    doses_totais_vacina INT,
    tem_proxima_dose_vacina BOOLEAN NOT NULL,   -- Informa se há mais alguma dose de vacina
    dtproxima_dose_vacina DATE,                -- Informa data da próxima dose de vacina, se é coluna anterior for TRUE
    valor_vacina DECIMAL(6, 2),
    PRIMARY KEY (pk_idvacina),
    FOREIGN KEY (fk_idpet_vacina)
        REFERENCES pet (pk_idpet),
    FOREIGN KEY (fk_idnome_vac_vacina)
        REFERENCES nome_vac (pk_idnome_vac),
    FOREIGN KEY (fk_idtipo_vac_vacina)
        REFERENCES tipo_vac (pk_idtipo_vac)
);

CREATE TABLE IF NOT EXISTS categoria_prod (
    pk_idcategoria_prod INT NOT NULL AUTO_INCREMENT,
    nome_categoria_prod VARCHAR(50) NOT NULL,
    PRIMARY KEY (pk_idcategoria_prod)
);

CREATE TABLE IF NOT EXISTS produto (
    pk_idproduto INT NOT NULL AUTO_INCREMENT,
    fk_idcategoria_prod_produto INT NOT NULL,
    descricao_produto VARCHAR(500),
    nome_produto VARCHAR(70) NOT NULL,
    fabricante_produto VARCHAR(100),
    PRIMARY KEY (pk_idproduto),
    FOREIGN KEY (fk_idcategoria_prod_produto)
		REFERENCES categoria_prod (pk_idcategoria_prod)
);

CREATE TABLE IF NOT EXISTS estoque (
    pk_idestoque INT NOT NULL AUTO_INCREMENT,
    fk_idproduto_estoque INT NOT NULL,
    quantidade_estoque INT NOT NULL,
    valor_custo_estoque DECIMAL(8,2),
    valor_venda_estoque DECIMAL(8,2),
    validade_estoque DATE,
    fabricacao_estoque DATE,
    aquisicao_estoque DATE,
    PRIMARY KEY (pk_idestoque),
    FOREIGN KEY (fk_idproduto_estoque)
        REFERENCES produto (pk_idproduto)
);

CREATE TABLE IF NOT EXISTS prescricao (
    pk_idprescricao INT NOT NULL AUTO_INCREMENT,
    fk_idpet_prescricao INT NOT NULL,
    fk_idveterinario_prescricao INT NOT NULL,
    fk_idclinica_prescricao INT NOT NULL,
    idatendimento_prescricao INT,
    observacao_prescricao VARCHAR(700),
    data_prescricao DATE NOT NULL,
    PRIMARY KEY (pk_idprescricao),
    FOREIGN KEY (fk_idpet_prescricao)
        REFERENCES pet (pk_idpet),
	FOREIGN KEY (fk_idclinica_prescricao)
        REFERENCES clinica (pk_idclinica),
	FOREIGN KEY (fk_idveterinario_prescricao)
		REFERENCES veterinario (pk_idveterinario)
);

CREATE TABLE IF NOT EXISTS produto_prescrito (
    pk_idproduto_prescrito INT NOT NULL AUTO_INCREMENT,
    fk_idprescricao_produto_prescrito INT NOT NULL,
    forma_uso_produto_prescrito VARCHAR(50) NOT NULL,
    nome_produto_prescrito VARCHAR(50) NOT NULL,
    quantidade_produto_prescrito VARCHAR(20) NOT NULL,
    posologia_produto_prescrito VARCHAR(250),
    PRIMARY KEY (pk_idproduto_prescrito),
    FOREIGN KEY (fk_idprescricao_produto_prescrito)
        REFERENCES prescricao (pk_idprescricao)
);

CREATE TABLE IF NOT EXISTS formatacao_prescricao (
    pk_idformatacao_prescricao INT NOT NULL AUTO_INCREMENT,
    item INT NOT NULL,   -- Especifica a qual item da prescrição a formatação se refere
    texto VARCHAR(250),
    tamanho INT,
    cor VARCHAR(20),
    negrito BOOLEAN,
    italico BOOLEAN,
    recuo INT,
    modelo INT,         -- Define a qual modelo de prescrição essa formatação pertence
    alinhamento INT,
    espacamento INT,
    fonte VARCHAR(50),
    presente BOOLEAN NOT NULL DEFAULT 1,  -- Se refere à presença ou não do item na prescrição
    modelo_ativo BOOLEAN NOT NULL,        -- Informa se o modelo dessa formatação é o que está ativo no momento
    PRIMARY KEY (pk_idformatacao_prescricao)
);

CREATE TABLE IF NOT EXISTS estilo (
    pk_idestilo INT NOT NULL AUTO_INCREMENT,
    nome_estilo VARCHAR(50) NOT NULL,
    ativo_estilo BOOLEAN NOT NULL,
    PRIMARY KEY (pk_idestilo)
);

CREATE TABLE IF NOT EXISTS preferencia (
    pk_idpreferencia INT NOT NULL AUTO_INCREMENT,
    nome_preferencia INT NOT NULL,
    escolha_preferencia INT NOT NULL,
    PRIMARY KEY (pk_idpreferencia)
);

CREATE TABLE IF NOT EXISTS sala_espera (
    pk_idsala_espera INT NOT NULL AUTO_INCREMENT,
    fk_idpet_sala_espera INT NOT NULL,
    chegada_sala_espera TIME,
    agendado_sala_espera BOOLEAN,
    horario_agendado_sala_espera TIME,
    urgencia_sala_espera BOOLEAN NOT NULL,
    PRIMARY KEY (pk_idsala_espera),
    FOREIGN KEY (fk_idpet_sala_espera)
        REFERENCES pet (pk_idpet)
);


CREATE TABLE IF NOT EXISTS internado (
    pk_idinternado INT NOT NULL AUTO_INCREMENT,
    fk_idpet_internado INT NOT NULL,
    fk_idveterinario_internado INT NOT NULL,
    dtinternacao_internado DATE NOT NULL,
    dtalta_internado DATE,
    valor_diaria_internado DECIMAL(8 , 2 ),
    valor_total_internado DECIMAL(9 , 2 ),
    observacoes_internado VARCHAR(500),
    ativo_internado BOOLEAN NOT NULL,
    PRIMARY KEY (pk_idinternado),
    FOREIGN KEY (fk_idpet_internado)
        REFERENCES pet (pk_idpet),
    FOREIGN KEY (fk_idveterinario_internado)
        REFERENCES veterinario (pk_idveterinario)
);

CREATE TABLE IF NOT EXISTS diaria_internacao (
    pk_iddiaria_internacao INT NOT NULL AUTO_INCREMENT,
    fk_idinternado_diaria_internacao INT NOT NULL,
    numero_diaria_internacao INT NOT NULL,
    notas_diaria_internacao VARCHAR(300),
    tratamento_diaria_internacao VARCHAR(500),
    data_diaria_internacao DATE NOT NULL,
    sinais_clinicos_diaria_internacao VARCHAR(200),
    PRIMARY KEY (pk_iddiaria_internacao),
    FOREIGN KEY (fk_idinternado_diaria_internacao)
        REFERENCES internado (pk_idinternado)
);

CREATE TABLE IF NOT EXISTS consumo_internacao (
    pk_idconsumo_internacao INT NOT NULL AUTO_INCREMENT,
    fk_iddiaria_internacao_consumo_internacao INT NOT NULL,
    fk_idestoque_consumo_internacao INT NOT NULL,
    quantidade_consumo_internacao INT NOT NULL,
    PRIMARY KEY (pk_idconsumo_internacao),
    FOREIGN KEY (fk_iddiaria_internacao_consumo_internacao)
        REFERENCES diaria_internacao (pk_iddiaria_internacao),
    FOREIGN KEY (fk_idestoque_consumo_internacao)
        REFERENCES estoque (pk_idestoque)
);

CREATE TABLE IF NOT EXISTS diaria_exame (
    pk_iddiaria_exame INT NOT NULL AUTO_INCREMENT,
    fk_iddiaria_internacao_diaria_exame INT NOT NULL,
    fk_idexame_realizado_diaria_exame INT NOT NULL,
    PRIMARY KEY (pk_iddiaria_exame),
    FOREIGN KEY (fk_iddiaria_internacao_diaria_exame)
        REFERENCES diaria_internacao (pk_iddiaria_internacao),
    FOREIGN KEY (fk_idexame_realizado_diaria_exame)
        REFERENCES exame_realizado (pk_idexame_realizado)
);

CREATE TABLE IF NOT EXISTS diaria_servico (
    pk_iddiaria_servico INT NOT NULL AUTO_INCREMENT,
    fk_iddiaria_internacao_diaria_servico INT NOT NULL,
    fk_idservico_realizado_diaria_servico INT NOT NULL,
    PRIMARY KEY (pk_iddiaria_servico),
    FOREIGN KEY (fk_iddiaria_internacao_diaria_servico)
        REFERENCES diaria_internacao (pk_iddiaria_internacao),
    FOREIGN KEY (fk_idservico_realizado_diaria_servico)
        REFERENCES servico_realizado (pk_idservico_realizado)
);

CREATE TABLE IF NOT EXISTS diaria_vacina (
    pk_iddiaria_vacina INT NOT NULL AUTO_INCREMENT,
    fk_iddiaria_internacao_diaria_vacina INT NOT NULL,
    fk_idvacina_diaria_vacina INT NOT NULL,
    PRIMARY KEY (pk_iddiaria_vacina),
    FOREIGN KEY (fk_iddiaria_internacao_diaria_vacina)
        REFERENCES diaria_internacao (pk_iddiaria_internacao),
    FOREIGN KEY (fk_idvacina_diaria_vacina)
        REFERENCES vacina (pk_idvacina)
);

CREATE TABLE IF NOT EXISTS valores_padrao (
	pk_idvalores_padrao INT NOT NULL AUTO_INCREMENT,
    codigo_valores_padrao INT NOT NULL,
    valor_valores_padrao DECIMAL(8,2),
    string_valores_padrao VARCHAR(60),
    PRIMARY KEY (pk_idvalores_padrao)
);

CREATE TABLE IF NOT EXISTS venda (
    pk_idvenda INT NOT NULL AUTO_INCREMENT,
    datahora_venda DATETIME NOT NULL,
    valortotal_venda DECIMAL(8 , 2 ) NOT NULL,
    nome_venda VARCHAR(80),
    cpf_venda VARCHAR(14),
    vendedor_venda VARCHAR(80),
    numeroparcelas_venda INT NOT NULL,
    formapagamento_venda INT NOT NULL,
    PRIMARY KEY (pk_idvenda)
);

CREATE TABLE IF NOT EXISTS venda_estoque (
    pk_idvenda_estoque INT NOT NULL AUTO_INCREMENT,
    fk_idvenda INT NOT NULL,
    fk_idestoque INT NOT NULL,
    qtd_consumida_venda_estoque INT NOT NULL,
    PRIMARY KEY (pk_idvenda_estoque),
    FOREIGN KEY (fk_idestoque)
        REFERENCES estoque (pk_idestoque),
    FOREIGN KEY (fk_idvenda)
        REFERENCES venda (pk_idvenda)
);









