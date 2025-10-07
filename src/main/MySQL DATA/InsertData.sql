INSERT INTO `368vd_zaposleni`
(`zap_mbr_z`,
`zap_ime`,
`zap_prezime`)
VALUES
(7, "Petar", "Peric"),
(101, "Ana", "Davic"),
(102, "Vuk", "Maric"),
(368, "Viktor", "Viktorovic"),
(505, "Andrej", "Andric"),
(707, "Toma", "Tomic");

INSERT INTO `368vd_manager`
(`zap_mbr_z`,
`plata`)
VALUES
(7, 60000),
(368, 70000),
(707, 10000);

INSERT INTO `368vd_radnik`
(`zap_mbr_z`,
`staz`)
VALUES
(101, 1),
(102, 5),
(505, 6);

INSERT INTO `368vd_departman`
(`naziv_d`,
`tip_d`,
`datum_pos`,
`manager_zap_mbr_z`)
VALUES
("DoF", "finance", "2024-08-13", 7),
("DoI", "IT", "2020-07-15", 368),
("DoH", "HR", "2019-05-21", 707);

INSERT INTO `368vd_projekat`
(`naziv_p`,
`sifra_p`,
`departman_mbr_z`)
VALUES
("alpha", 1, 7),
("beta", 3, 7),
("omega", 4, 7),
("delta", 2, 368),
("gamma", 5, 707);

INSERT INTO `368vd_radi_na`
(`radnik_zap_mbr_z`,
`projekat_sifra_p`,
`brcasova`)
VALUES
(101, 1, 5),
(101, 2, 50),
(101, 3, 12),
(101, 4, 15),
(102, 1, 9),
(102, 2, 16),
(505, 5, 10);




