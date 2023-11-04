import java.util.ArrayList;
import java.util.Comparator;

public class Processo {
    int id = 0;
    int encarnacao = 0;
    int tempoConserto = 0;
    int errosPorTempo = 0;
    ArrayList<Processo> proxLider = new ArrayList<Processo>();

    Processo(int id, int encarnacao, int errosPorTempo) {
        this.id = id;
        this.encarnacao = encarnacao;
        this.errosPorTempo = errosPorTempo;
    }

    public void addLider(Processo proxProcesso) {
        if (!proxLider.contains(proxProcesso)) {
            proxLider.add(proxLider.size(), proxProcesso);
            proxLider.sort(Comparator.comparing(Processo::getEncarnacao));
        }
        // proxProcesso.imprimirProcesso();
        // System.out.println("------------> HeartBeats Para");
        // imprimirProcesso();

        // System.out.println("\n---------------------------------\n");
    }

    public void setErrosPorTempo(int errosPorTempo) {
        this.errosPorTempo = errosPorTempo;
    }

    public int getErrosPorTempo() {
        return errosPorTempo;
    }

    public void setTempoConserto(int tempoConserto) {
        this.tempoConserto = tempoConserto;
    }

    public int diminuirTempoConserto() {
        return --tempoConserto;
    }

    public int getTempoConserto() {
        return tempoConserto;
    }

    public int getId() {
        return id;
    }

    public int getEncarnacao() {
        return encarnacao;
    }

    public void addEncarnacao() {
        this.encarnacao++;
    }

    public void heartBeats() {

    }

    public void imprimirProcesso() {
        System.out.println("Id:" + id);
        System.out.println("Encarnação:" + encarnacao);
        // System.out.println();
    }

    public int votarLider() {
        // Compara a encarnação dele mesmo com o menor da lista
        if (proxLider.size() > 0) {
            return proxLider.get(0).getEncarnacao() <= getEncarnacao() ? proxLider.get(0).getId() : getId();
        }

        return getId();
    }

    public boolean testaFalha(int i) {
        return i % getErrosPorTempo() == 0;
    }
}
