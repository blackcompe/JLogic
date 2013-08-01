import java.util.*;
import java.lang.*;

public class LogicSolver
{
	private static boolean debug = true; //'true' to display replacements

	private static List<String> exprlist = new ArrayList<String>();
	private static Map<Character, Boolean> map = new HashMap<Character, Boolean>();
	
	static 
	{
		map.put('T', true);
		map.put('F', false);
	}

	private static boolean solve(String expr, char pTruth, char qTruth, char rTruth)
	{
		exprlist.clear();
		exprlist.add(expr);
		expr = substitute(expr, pTruth, qTruth, rTruth);
		exprlist.add(expr);
		boolean b = _solve(expr);
		if(debug)
			System.out.println(exprlist);
		return b;
	}

	private static boolean _solve(String expr)
	{
		Pair p;
		String val = null;
		while((p = next(expr)) != null)
		{
			String sub = expr.substring(p.start, p.end+1);
			val = eval(sub);
			expr = expr.replaceFirst(escape(sub), escape(val));
			exprlist.add(expr);
		}
		return (val.equals("T")) ? true : false;
	}

	/* Replace symbols with their truth values */
	private static String substitute(String expr, char pTruth, char qTruth, char rTruth)
	{
		return expr.replace('P', pTruth).replace('Q', qTruth).replace('R', rTruth);
	}

	/* Assumes 'expr' has three characters. Returns 'T' is expr is true, otherwise 'F'.*/
	private static String eval(String expr)
	{
		boolean b1 = map.get(expr.charAt(0));
		boolean b2 = map.get(expr.charAt(1));
		switch(expr.charAt(2))
		{
			case '&':
				return (b1 && b2) ? "T" : "F";
			case '|':
				return (b1 || b2) ? "T" : "F";
		}
		return null;
	}

	/* Find location of next expression. Return null otherwise. */
	private static Pair next(String expr)
	{
		int i = expr.indexOf('&');
		int i2 = expr.indexOf('|');
		if(i >= 0 && i2 >= 0 && i2 < i)
			i = i2;
		else if(i < 0 && i2 >= 0)
			i = i2;
		return (i < 0) ? null : new Pair(i-2, i);
	}

	private static class Pair
	{
		int start, end;
		Pair(int s, int e){start = s; end = e;}
	}

	/* Escape regex meta-characters */
	private static String escape(String s)
	{
		return s.replace("|", "\\|").replace("&", "\\&");
	}
	
	public static void main (String[] args) throws java.lang.Exception
	{
		//(P&R)|(Q|R)	
		System.out.println(solve("PR&QR||", 'T', 'T', 'T')); //true
		System.out.println(solve("PR&QR||", 'T', 'F', 'F')); //false

                //(P&R)&((P&Q)|(R|Q))
		System.out.println(solve("PR&PQ&RQ||&", 'T', 'F', 'T')); //true
		System.out.println(solve("PR&PQ&RQ||&", 'F', 'T', 'T')); //false

		//((P&Q)&((P&R)|(Q|R)))&((Q&Q)&(P|R))
		System.out.println(solve("PQ&PR&QR||&QQ&PR|&&", 'T', 'T', 'T')); //true
		System.out.println(solve("PQ&PR&QR||&QQ&PR|&&", 'T', 'T', 'F')); //true
		System.out.println(solve("PQ&PR&QR||&QQ&PR|&&", 'T', 'F', 'F')); //false

	}
}
