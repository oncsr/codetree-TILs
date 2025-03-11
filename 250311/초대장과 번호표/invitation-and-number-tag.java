import java.util.*;
import java.io.*;


class Main {
	
	// IO field
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;

	static void nextLine() throws Exception {st = new StringTokenizer(br.readLine());}
	static int nextInt() {return Integer.parseInt(st.nextToken());}
	static long nextLong() {return Long.parseLong(st.nextToken());}
	static void bwEnd() throws Exception {bw.flush();bw.close();}
	
	// Additional field

	static int N, G;
	static TreeSet<Integer>[] T;
	static TreeSet<Integer>[] S;
	static boolean[] vis;
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		nextLine();
		N = nextInt();
		G = nextInt();
		T = new TreeSet[N+1];
		for(int i=1;i<=N;i++) T[i] = new TreeSet<>();
		S = new TreeSet[G+1];
		for(int i=1;i<=G;i++) S[i] = new TreeSet<>();
		
		for(int i=1;i<=G;i++) {
			nextLine();
			for(int n=nextInt(),a;n-->0;T[a].add(i)) {
				a = nextInt();
				S[i].add(a);
			}
		}
		
	}
	
	static void solve() throws Exception{
		
		Queue<Integer> Q = new LinkedList<>();
		vis = new boolean[N+1];
		Q.add(1);
		vis[1] = true;
		int cnt = 0;
		while(!Q.isEmpty()) {
			int now = Q.poll();
			cnt++;
			for(int i:T[now]) {
				S[i].remove(now);
				if(S[i].size() == 1) {
					int a = S[i].first();
					if(!vis[a]) {
						vis[a] = true;
						Q.add(a);
					}
				}
			}
		}
		bw.write(cnt + "\n");
		
	}
	
}