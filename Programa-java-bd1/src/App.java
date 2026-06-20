import model.Aluno;
import model.Curso;
import model.Disciplina;
import dao.AlunoDAO;
import dao.CursoDAO;
import dao.DisciplinaDAO;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlunoDAO alunoDao = new AlunoDAO();
        CursoDAO cursoDao = new CursoDAO();
        DisciplinaDAO disciplinaDao = new DisciplinaDAO();
        
        int opcaoPrincipal = 0;

        while (opcaoPrincipal != 2) {
            System.out.println("\n--- SISTEMA DE BUSCA DA UNIVERSIDADE ---");
            System.out.println("1. Pesquisar");
            System.out.println("2. Sair\n");
            System.out.print("Escolha uma opcao: ");
            
            opcaoPrincipal = scanner.nextInt();
            scanner.nextLine();

            if (opcaoPrincipal == 1) {
                System.out.println("\n--- OPCOES DE PESQUISA ---");
                System.out.println("1. Pesquisar alunos por Curso");
                System.out.println("2. Pesquisar alunos por Materia e Semestre\n");
                System.out.print("Escolha o tipo de pesquisa: ");
                
                int opcaoPesquisa = scanner.nextInt();
                scanner.nextLine();

                switch (opcaoPesquisa) {
                    case 1:
                        List<Curso> cursos = cursoDao.listarTodos();
                        
                        if (cursos.isEmpty()) {
                            System.out.println("\nNenhum curso cadastrado no banco.");
                            break;
                        }

                        System.out.println("\n--- ESCOLHA O CURSO ---\n");
                        for (int i = 0; i < cursos.size(); i++) {
                            System.out.println((i + 1) + ". " + cursos.get(i).getNome());
                        }
                        
                        System.out.print("Digite o numero do curso desejado: ");
                        int escolhaCurso = scanner.nextInt();
                        scanner.nextLine();

                        if (escolhaCurso > 0 && escolhaCurso <= cursos.size()) {
                            String cursoAlvo = cursos.get(escolhaCurso - 1).getNome();
                            
                            List<Aluno> alunosCurso = alunoDao.buscarPorCurso(cursoAlvo);
                            imprimirResultados(alunosCurso, "Curso: " + cursoAlvo);
                        } else {
                            System.out.println("\nNumero de curso invalido.");
                        }
                        break;

                    case 2:
                        List<Disciplina> disciplinas = disciplinaDao.listarTodas();
                        
                        if (disciplinas.isEmpty()) {
                            System.out.println("\nNenhuma disciplina cadastrada no banco.");
                            break;
                        }

                        System.out.println("\n--- ESCOLHA A MATERIA ---\n");
                        for (int i = 0; i < disciplinas.size(); i++) {
                            System.out.println((i + 1) + ". " + disciplinas.get(i).getNome_disciplina());
                        }
                        
                        System.out.print("Digite o numero da materia: ");
                        int escolhaMateria = scanner.nextInt();
                        scanner.nextLine();

                        if (escolhaMateria > 0 && escolhaMateria <= disciplinas.size()) {
                            String materiaAlvo = disciplinas.get(escolhaMateria - 1).getNome_disciplina();
                            
                            System.out.println("--- ESCOLHA O SEMESTRE ---\n");
                            System.out.println("1. Semestre Atual (2026.1)");
                            System.out.println("2. Semestre Passado (2025.2)\n");
                            System.out.print("Digite o numero do semestre: ");
                            
                            int escolhaSemestre = scanner.nextInt();
                            scanner.nextLine();
                            
                            String semestreAlvo = "";
                            if (escolhaSemestre == 1) {
                                semestreAlvo = "2026.1";
                            } else if (escolhaSemestre == 2) {
                                semestreAlvo = "2025.2";
                            } else {
                                System.out.println("\nNumero de semestre invalido.");
                                break;
                            }

                            List<Aluno> alunosMateria = alunoDao.buscarPorDisciplinaESemestre(materiaAlvo, semestreAlvo);
                            imprimirResultados(alunosMateria, "Materia: " + materiaAlvo + " | Semestre: " + semestreAlvo);
                        } else {
                            System.out.println("\nNumero de materia invalido.");
                        }
                        break;

                    default:
                        System.out.println("\nOpcao de pesquisa invalida.");
                }
            } else if (opcaoPrincipal == 2) {
                System.out.println("\nEncerrando o sistema.");
            } else {
                System.out.println("\nOpcao invalida. Tente novamente.");
            }
        }
        
        scanner.close();
    }

    private static void imprimirResultados(List<Aluno> alunos, String tituloFiltro) {
        if (alunos.isEmpty()) {
            System.out.println("\nNenhum aluno encontrado para o filtro: " + tituloFiltro);
        } else {
            System.out.println("\n--- ALUNOS ENCONTRADOS ---\n");
            System.out.println("Filtro: " + tituloFiltro);
            for (Aluno aluno : alunos) {
                System.out.println("Matricula: " + aluno.getMatricula() + " | Nome: " + aluno.getNome());
            }
            System.out.println("-------------------------");
        }
    }
}