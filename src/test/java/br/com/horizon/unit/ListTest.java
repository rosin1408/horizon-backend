package br.com.horizon.unit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class ListTest {

    private static final long INTERACOES = 100000L;

    @Test
    public void test() {
        List<Dto> arrayListVazio = new ArrayList<>();
        List<Dto> arrayListInicializado = new ArrayList<>(100005);
        List<Dto> linkedList = new LinkedList<>();
        Set<Dto> hashSet = new HashSet<>();
        Set<Dto> hashSetInicializado = new HashSet<>(100005);

        System.out.println("Inicializar");

        long initTime = System.nanoTime();

        for (long i = 0; i < INTERACOES; i++) {
            arrayListVazio.add(new Dto());
        }

        long endTime = System.nanoTime();

        long totalTime = endTime - initTime;

        System.out.println("ArrayList()     " + totalTime);

        initTime = System.nanoTime();

        for (long i = 0; i < INTERACOES; i++) {
            arrayListInicializado.add(new Dto());
        }

        endTime = System.nanoTime();

        totalTime = endTime - initTime;

        System.out.println("ArrayList(1005) " + totalTime);

        initTime = System.nanoTime();

        for (long i = 0; i < INTERACOES; i++) {
            linkedList.add(new Dto());
        }

        endTime = System.nanoTime();

        totalTime = endTime - initTime;

        System.out.println("LinkedList      " + totalTime);

        initTime = System.nanoTime();

        for (long i = 0; i < INTERACOES; i++) {
            hashSet.add(new Dto());
        }

        endTime = System.nanoTime();

        totalTime = endTime - initTime;

        System.out.println("HashSet         " + totalTime);

        initTime = System.nanoTime();

        for (long i = 0; i < INTERACOES; i++) {
            hashSetInicializado.add(new Dto());
        }

        endTime = System.nanoTime();

        totalTime = endTime - initTime;

        System.out.println("HashSet(100005) " + totalTime);

        System.out.println("get");

        initTime = System.nanoTime();
        for (int i = 0; i < INTERACOES; i++) {
            arrayListVazio.get(i);
        }
        endTime = System.nanoTime();

        totalTime = endTime - initTime;
        System.out.println("ArrayList       " + totalTime);

        initTime = System.nanoTime();
        for (int i = 0; i < INTERACOES; i++) {
            linkedList.get(i);
        }
        endTime = System.nanoTime();

        totalTime = endTime - initTime;
        System.out.println("LinkedList      " + totalTime);

        System.out.println("Iterate");

        initTime = System.nanoTime();
        arrayListVazio.forEach(a -> a.nome = "Batata");
        endTime = System.nanoTime();

        totalTime = endTime - initTime;
        System.out.println("ArrayList       " + totalTime);

        initTime = System.nanoTime();
        linkedList.forEach(a -> a.nome = "Batata");
        endTime = System.nanoTime();

        totalTime = endTime - initTime;
        System.out.println("LinkedList      " + totalTime);

        initTime = System.nanoTime();
        hashSet.forEach(a -> a.nome = "Batata");
        endTime = System.nanoTime();

        totalTime = endTime - initTime;
        System.out.println("HashSet         " + totalTime);
    }

    @Test
    public void outroTeste() {

        ArrayList arrayList = new ArrayList();
        LinkedList linkedList = new LinkedList();

        // ArrayList add
        long startTime = System.nanoTime();

        for (int i = 0; i < INTERACOES; i++) {
            arrayList.add(i);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("ArrayList add:     " + duration);

        // LinkedList add
        startTime = System.nanoTime();

        for (int i = 0; i < INTERACOES; i++) {
            linkedList.add(i);
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("LinkedList add:    " + duration);

        // ArrayList get
        startTime = System.nanoTime();

        for (int i = 0; i < INTERACOES; i++) {
            arrayList.get(i);
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("ArrayList get:     " + duration);

        // LinkedList get
        startTime = System.nanoTime();

        for (int i = 0; i < INTERACOES; i++) {
            linkedList.get(i);
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("LinkedList get:    " + duration);

        // ArrayList remove
        startTime = System.nanoTime();

        for (int i = 9999; i >= 0; i--) {
            arrayList.remove(i);
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("ArrayList remove:  " + duration);

        // LinkedList remove
        startTime = System.nanoTime();

        for (int i = 9999; i >= 0; i--) {
            linkedList.remove(i);
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("LinkedList remove: " + duration);

    }

    class Dto {
        private Long id;
        private String nome;
        private String sobrenome;

        public Dto() {
            this.id = 2091L;
            this.nome = "Roberto";
            this.sobrenome = "Rosin";
        }
    }
}
