package tst;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import app.IRPF;

@RunWith(Parameterized.class)
public class TesteBaseCalculo {
	IRPF irpf;
	Object[][] rendimentos;
	Object[][] outrasDeducoes;
	float[] previdencias;
	Object[][] dependentes;
	Object[][] pensoes;
	float baseCalculo;
	
	public TesteBaseCalculo(Object[][] rendimentos, Object[][] outrasDeducoes, float[] previdencias, Object[][] dependentes, Object[][] pensoes, float baseCalculo) {
		this.rendimentos = rendimentos.clone();
		this.outrasDeducoes = outrasDeducoes.clone();
		this.previdencias = previdencias.clone();
		this.dependentes = dependentes.clone();
		this.pensoes = pensoes.clone();
		this.baseCalculo = baseCalculo;
	}
	
	@Before
	public void setup() {
		irpf = new IRPF();
		
		for(int i = 0; i < rendimentos.length; i++) {
			if((boolean) rendimentos[i][1])
				irpf.criarRendimento(rendimentos[i][0].toString(), irpf.TRIBUTAVEL, (float)rendimentos[i][2]);
			else
				irpf.criarRendimento(rendimentos[i][0].toString(), irpf.NAOTRIBUTAVEL, (float)rendimentos[i][2]);
		}

		for(int i = 0; i < outrasDeducoes.length; i++)
			irpf.cadastrarDeducaoIntegral(outrasDeducoes[i][0].toString(), (float)outrasDeducoes[i][1]);
		
		for(int i = 0; i < previdencias.length; i++)
			irpf.cadastrarContribuicaoPrevidenciaria(previdencias[i]);
		
		for(int i = 0; i < dependentes.length; i++)
			irpf.cadastrarDependente(dependentes[i][0].toString(), dependentes[i][1].toString());
		
		for(int i = 0; i < pensoes.length; i++)
			irpf.cadastrarPensaoAlimenticia(pensoes[i][0].toString(), (float)pensoes[i][1]);
	}
	
	@Parameters
	public static Iterable getParameters() {
		Object[][] rendimentos1 = {
				{"Salário", true, 8000f}, 
				{"Aluguel", true, 2000f}, 
				{"Bolsa de estudos", false, 1500f}
		};
		Object[][] outrasDeducoes1 = {{"prev. privada", 1000f}};
		float[] previdencias1 = {500f};
		Object[][] dependentes1 = {{"João", "filho"}};
		Object[][] pensoes1 = {{"João", 1500f}};
		
		Object[][] parametros = new Object[][] {
			{rendimentos1, outrasDeducoes1, previdencias1, dependentes1, pensoes1, 6810.41f}
		};

		return Arrays.asList(parametros);
	}

	@Test
	public void test() {
		assertEquals(irpf.getBaseCalculo(), baseCalculo, 0f);
	}

}