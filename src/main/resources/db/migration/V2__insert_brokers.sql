DO
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM broker) THEN
        INSERT INTO broker (id, name, cnpj, created_at) VALUES
            (uuid_generate_v4(), 'AGORA CTVM S.A.', '74014747000135', now()),
            (uuid_generate_v4(), 'ATIVA INVESTIMENTOS S.A. C.T.C.V.', '33775974000104', now()),
            (uuid_generate_v4(), 'BANRISUL S.A. CORRETORA DE VALORES MOBILIÁRIOS E CÂMBIO', '93026847000126', now()),
            (uuid_generate_v4(), 'BB-BI', '24933830000130', now()),
            (uuid_generate_v4(), 'BGC LIQUIDEZ DISTRIBUIDORA DE TÍTULOS E VALORES MOBILIÁRIOS LTDA.', '33862244000132', now()),
            (uuid_generate_v4(), 'BRADESCO S/A CTVM', '61855045000132', now()),
            (uuid_generate_v4(), 'BTG PACTUAL CTVM S/A', '43815158000122', now()),
            (uuid_generate_v4(), 'C6 CORRETORA DE TÍTULOS E VALORES MOBILIÁRIOS LTDA.', '32345784000186', now()),
            (uuid_generate_v4(), 'CITIGROUP GLOBAL MARKETS BRASIL, CCTVM S/A', '33709114000164', now()),
            (uuid_generate_v4(), 'CM CAPITAL MARKETS CCTVM LTDA', '02685483000130', now()),
            (uuid_generate_v4(), 'CREDIT SUISSE (BRASIL) S/A CTVM', '42584318000107', now()),
            (uuid_generate_v4(), 'GENIAL INSTITUCIONAL CCTVM S.A.', '05816451000115', now()),
            (uuid_generate_v4(), 'GENIAL INVESTIMENTOS CORRETORA DE VALORES MOBILIÁRIOS S.A.', '27652684000162', now()),
            (uuid_generate_v4(), 'GOLDMAN SACHS DO BRASIL CTVM S/A', '09605581000160', now()),
            (uuid_generate_v4(), 'ICAP DO BRASIL CTVM LTDA', '09105360000122', now()),
            (uuid_generate_v4(), 'IDEAL CORRETORA DE TÍTULOS E VALORES MOBILIÁRIOS S.A.', '31749596000150', now()),
            (uuid_generate_v4(), 'INTER DISTRIBUIDORA DE TÍTULOS E VALORES MOBILIÁRIOS', '18945670000146', now()),
            (uuid_generate_v4(), 'ITAU CORRETORA DE VALORES SA', '61194353000164', now()),
            (uuid_generate_v4(), 'J. P. MORGAN CCVM S.A.', '32588139000194', now()),
            (uuid_generate_v4(), 'LEV DISTRIBUIDORA DE TITULOS E VALORES MOBILIARIOS LTDA', '45457891000148', now()),
            (uuid_generate_v4(), 'MERRILL LYNCH S/A CORRETORA DE TÍTULOS E VALORES MOBILIÁRIOS', '02670590000195', now()),
            (uuid_generate_v4(), 'MIRAE ASSET (BRASIL) CORRETORA DE CÂMBIO, TÍTULOS E VALORES MOBILIÁRIOS LTDA.', '12392983000138', now()),
            (uuid_generate_v4(), 'MORGAN STANLEY CTVM S.A.', '04323351000194', now()),
            (uuid_generate_v4(), 'NOVA FUTURA CTVM LTDA', '04257795000179', now()),
            (uuid_generate_v4(), 'Nu Investimentos S.A. - Corretora de Títulos e Valores Mobiliários', '62169875000179', now()),
            (uuid_generate_v4(), 'PLANNER CORRETORA DE VALORES S.A.', '00806535000154', now()),
            (uuid_generate_v4(), 'RB INVESTIMENTOS DISTRIBUIDORA DE TÍTULOS E VALORES MOBILIÁRIOS LTDA.', '89960090000176', now()),
            (uuid_generate_v4(), 'RENASCENCA DISTRIBUIDORA DE TITULOS E VALORES MOBILIARIOS LTDA.', '62287735000103', now()),
            (uuid_generate_v4(), 'SAFRA DISTRIBUIDORA DE TÍTULOS E VALORES MOBILIÁRIOS LTDA.', '01638542000157', now()),
            (uuid_generate_v4(), 'SANTANDER CCVM S/A', '51014223000149', now()),
            (uuid_generate_v4(), 'SCOTIABANK BRASIL S.A. CORRETORA DE TÍTULOS E VALORES MOBILIÁRIOS', '39696805000157', now()),
            (uuid_generate_v4(), 'STONEX DISTRIBUIDORA DE TÍTULOS E VALORES MOBILIÁRIOS LTDA', '62090873000190', now()),
            (uuid_generate_v4(), 'TERRA INVESTIMENTOS DISTRIBUIDORA DE TITULOS E VALORES MOBILIARIOS LTDA', '03751794000113', now()),
            (uuid_generate_v4(), 'TORO CORRETORA DE TÍTULOS E VALORES MOBILIARIOS S/A', '29162769000198', now()),
            (uuid_generate_v4(), 'TULLETT PREBON BRASIL CORRETORA DE VALORES E CÂMBIO LTDA', '61747085000160', now()),
            (uuid_generate_v4(), 'UBS (BRASIL) CORRETORA DE VALORES S.A', '61809182000130', now()),
            (uuid_generate_v4(), 'XP INVESTIMENTOS CCTVM S.A.', '02332886000104', now());
END IF;
END $$;