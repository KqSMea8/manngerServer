package com.lmxf.post.core.security;

import java.math.BigInteger;
import java.util.Random;

public class DHKeyGen {
	static Random r = new Random();
	static {
		r.setSeed(99999);
	}
	private BigInteger g = new BigInteger("9");
	private BigInteger p = new BigInteger("999999");
	private BigInteger priv;

	private BigInteger pub;
	private BigInteger pub2;
	private BigInteger key;

	public void genPrivKey() {
		int n;
		while (true) {
			n = r.nextInt(999999);
			if (n > 1)
				break;
		}
		priv = new BigInteger(String.valueOf(n));
		pub = g.modPow(priv, p);
		key = null;
	}

	public BigInteger getPub() {
		return pub;
	}

	public BigInteger getPub2() {
		return pub2;
	}
	public	void	setPub2(String pub2){
		this.pub2 = new BigInteger(pub2);
		key = null;
	}
	public void setPub2(BigInteger pub2) {
		this.pub2 = pub2;
		key = null;
	}

	public BigInteger genShareKey() {
		if (key != null)
			return null;

		return pub2.modPow(priv, p);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			DHKeyGen g1 = new DHKeyGen();
			DHKeyGen g2 = new DHKeyGen();
			g1.genPrivKey();
			g2.genPrivKey();
			System.out.println("============g1:" + g1.priv + ":" + g1.pub);
			System.out.println("============g2:" + g2.priv + ":" + g2.pub);
			// ������Կ
			g1.setPub2(g2.getPub());
			g2.setPub2(g1.getPub());

			BigInteger key1 = g1.genShareKey();
			BigInteger key2 = g2.genShareKey();
			System.out.println(key1 + ":" + key2);
		}
		System.out.println("========");
		BigInteger g = new BigInteger("9");
		BigInteger p = new BigInteger("999999");
		BigInteger priv = new BigInteger("99999999");
		BigInteger pub=g.modPow(priv,p);
		
		System.out.println(priv+":"+pub);
		
		BigInteger pub2=new BigInteger("279288");
		System.out.println(pub2.modPow(priv,p));

	}
}
