import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void resetaVotacao(int votacao[]) {
        for (int i = 0; i < votacao.length; i++) {
            votacao[i] = 0;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ArrayList<Processo> rede = new ArrayList<Processo>();
        Scanner sc = new Scanner(System.in);

        Random random = new Random();

        // System.out.println("Digite o numero de processos:");
        // String num = sc.nextLine();
        // int numProcessos = Integer.parseInt(num);
        int numProcessos = 10;

        int votacao[] = new int[numProcessos];

        // numeroErro = valor para provocar um erro no processo
        int numeroErro = random.nextInt(6);
        numeroErro += 3;

        rede.add(new Processo(0, 0, numeroErro));
        Processo lider = rede.get(0);
        votacao[0] = 0;

        for (int i = 1; i < numProcessos; i++) {
            numeroErro = random.nextInt(numProcessos);
            numeroErro += 3;
            rede.add(new Processo(i, 1, numeroErro));
            rede.get(i).addLider(rede.get(0));
            votacao[i] = 0;
        }

        int timeMensagem = 3000;
        for (int i = 1; i < numProcessos * 100; i++) {
            System.out.println("\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("Tempo atual:" + i);
            if (lider.tempoConserto == 0) // Caso todos os processos falhem
                System.out.println("Lider atual: Id " + lider.getId());
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n");

            for (Processo processoAtual : rede) {
                // Provoca um erro no processo
                // True = falhou
                if (!processoAtual.testaFalha(i)) {
                    if (processoAtual.getTempoConserto() == 0) {
                        // Simula o envio de mensagens do processoAtual para os outros
                        for (Processo processo : rede) {
                            if (processo != processoAtual) {
                                processo.addLider(processoAtual);
                            }
                        }
                    } else {
                        processoAtual.diminuirTempoConserto();
                    }
                } else {
                    // 1 a 2 tempos para conserta o processo
                    processoAtual.setTempoConserto(1 + random.nextInt((int) Math.round(Math.log(numProcessos))));

                    // Tirar o processo falho da lista de cada processo
                    // (pois ele não enviou mensagens no tempo atual)
                    for (Processo processo : rede) {
                        processo.proxLider.remove(processoAtual);
                    }

                    System.out.print("Processo falhou ");
                    processoAtual.imprimirProcesso();
                    System.out.println();
                    processoAtual.addEncarnacao();
                }
            } // Loop pelos processos no tempo i

            // Verificar se o lider atual deve ser trocado (pois falhou)
            if (lider.testaFalha(i)) {

                System.out.println("\nElegendo próximo lider\n");
                // Esperar um tempinho para ver a troca de lider
                TimeUnit.MILLISECONDS.sleep(timeMensagem);

                // Votação de cada processo que não esta falho
                for (Processo processo : rede) {
                    if (processo.getTempoConserto() == 0) {
                        if (!processo.testaFalha(i)) {
                            // Quanto menor a encarnacao mais votos
                            var alo = processo.votarLider();
                            votacao[alo]++;
                        }
                    }
                }

                System.out.println();
                int maior = 0;
                // Maior quantiade de votos
                for (int j = 0; j < votacao.length; j++) {
                    System.out.println("Id " + j + " - Votos: " + votacao[rede.get(j).getId()]);
                    if (votacao[rede.get(j).getId()] > votacao[rede.get(maior).getId()])
                        maior = j;
                }

                System.out.println();
                System.out.println("-------------------------");

                if (votacao[maior] > 0) {
                    System.out.println("Novo lider:\n");
                    lider = rede.get(maior);
                    lider.imprimirProcesso();
                } else {
                    System.out.println("Todos os processos estão falhos.\n");
                }
                // Reinicar o array "votação" para a votação seguinte
                resetaVotacao(votacao);

                System.out.println("-------------------------");
                TimeUnit.MILLISECONDS.sleep(timeMensagem);
            } // lider.testaFalha(i)

        } // contador do tempo

    } // main

}