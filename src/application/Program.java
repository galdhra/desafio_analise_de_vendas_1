package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		System.out.println();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();

			List<Sale> list = new ArrayList<>();

			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}

			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");

			int wantedYear = 2016;

			Comparator<Sale> comp = (s1, s2) -> s1.avaragePrice().compareTo(s2.avaragePrice());

			List<Sale> newList = list.stream().filter(x -> x.getYear() == wantedYear).sorted(comp.reversed()).limit(5)
					.collect(Collectors.toList());

			newList.forEach(System.out::println);

			System.out.println();

			double tLogan = list.stream()
					.filter((x -> x.getSeller().charAt(0) == 'L'))
					.filter(x -> x.getMonth() == 1 || x.getMonth() == 7)
					.map(x -> x.getTotal())
					.reduce(0.0, (x, y) -> x + y);

			System.out.printf("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f", tLogan);

		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}

		sc.close();
	}

}
