DROP DATABASE IF EXISTS Universidade;
CREATE DATABASE Universidade;
USE Universidade;

-- Tabela de Cursos
CREATE TABLE Cursos (
    id_curso INT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    PRIMARY KEY (id_curso)
);

-- Tabela de Disciplinas
-- Relacionamento: Um curso possui várias disciplinas (1:N)
CREATE TABLE Disciplinas (
    id_disciplina INT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    id_curso INT,
    PRIMARY KEY (id_disciplina),
    FOREIGN KEY (id_curso) REFERENCES Cursos(id_curso)
);

-- Tabela de Aluno
-- Relacionamento: Alunos se matriculam em um curso (1:N)
CREATE TABLE Aluno (
    matricula INT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    cpf INT NOT NULL,
    id_curso INT,
    PRIMARY KEY (matricula),
    FOREIGN KEY (id_curso) REFERENCES Cursos(id_curso)
);
CREATE TABLE Aluno_Disciplina (
    matricula INT NOT NULL,
    id_disciplina INT NOT NULL,
    semestre VARCHAR(10) NOT NULL,
    PRIMARY KEY (matricula, id_disciplina, semestre),
    FOREIGN KEY (matricula) REFERENCES Aluno(matricula),
    FOREIGN KEY (id_disciplina) REFERENCES Disciplinas(id_disciplina)
);
-- Populando as tabelas:

INSERT INTO Cursos (nome)
VALUES
('Ciencia da Computacao'),
('Fisica'),
('Engenharia Quimica');

INSERT INTO Disciplinas (nome)
VALUES
('calculo 1'),
('fisica 1'),
('quimica 1'),
('algebra linear'),
('bioquimica'),
('banco de dados');

INSERT INTO Aluno (nome, matricula, cpf, id_curso)
VALUES
('Joao Alexandre', 11111111, 98756765, 1), -- Ciência da Computação
('Julia Mendonca', 22222222, 92456743, 1), -- Ciência da Computação
('Marcos Antonio', 33333333, 38756725, 1), -- Ciência da Computação
('Hugo Francisco', 44444444, 97776745, 1), -- Ciência da Computação
('Maria Eduarda', 55555555 , 92736560, 2), -- Física
('Joao Pedro Dantas', 66666666, 18736765, 2), -- Física
('Marconny Marques', 77777777, 98756461, 2), -- Física
('Pedro Cesar', 88888888, 76518728, 3),    -- Engenharia Química
('Grazy Cristine', 99999999, 98252723, 3), -- Engenharia Química
('Pablo Rosa', 00000000, 92252723, 3);     -- Engenharia Química

INSERT INTO Aluno_Disciplina (matricula, id_disciplina, semestre) VALUES
(11111111, 1, '2026.1'), -- Joao Alexandre em Calculo 1 (Atual)
(22222222, 1, '2025.2'), -- Julia em Calculo 1 (Passado)
(66666666, 2, '2026.1'), -- Joao Pedro Dantas em Fisica 1 (Atual)
(55555555, 2, '2025.2'), -- Maria Eduarda em Fisica 1 (Passado)
(88888888, 3, '2026.1'), -- Pedro Cesar em Quimica 1 (Atual)
(99999999, 3, '2025.2'); -- Grazy em Quimica 1 (Passado)