package com.example.demo.solve;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		
		// 테스트 케이스의 수
		int t = sc.nextInt();

		// t를 감소시키면서 0이 될 때까지
		while (t-- > 0) {
			// 문서의 개수
			int n = sc.nextInt();

			// 몇 번째로 인쇄되었는지 궁금한 문서가
			// 현재 큐에서 몇 번째로 놓여 있는지를 나타내는 정수
			int m = sc.nextInt();

			// 큐 생성 LinkedList는 Queue를 상속 받고 있다.
			LinkedList<int[]> q = new LinkedList<>();

			// 큐에 문서의 위치 i와 함께 중요도를 입력한다.
			for (int i = 0; i < n; i++) {
				q.offer(new int[] { i, sc.nextInt() });
			}

			// 각 케이스마다 다시 초기화
			int count = 0;

			// 각 케이스별 큐가 모두 poll될 때까지 (최악의 경우)
			while (!q.isEmpty()) {

				// 인쇄 시도 (큐에서 꺼내짐)
				int[] front = q.poll();

				// 시도한 문서가 가장 중요한 문서임을 나타내는 변수 초기화
				boolean isMax = true;

				// 큐의 사이즈만큼 반복하며 중요도를 검사
				for (int i = 0; i < q.size(); i++) {

					// 시도된 문서의 중요도([1])가
					// 나머지 문서 중 첫 번째 문서보다 작으면
					// (시도된 문서가 다른 문서보다 중요하지 않으면)
					if (front[1] < q.get(i)[1]) {

						// 큐의 맨 뒤로 삽입
						q.offer(front);

						// front뿐만 아니라 가장 중요한 문서(i번 째 문서) 이전까지 문서들도
						// 전부 맨 뒤로 삽입
						for (int j = 0; j < i; j++) {
							q.offer(q.poll());
						}

						// if문에 들어왔으니 front는 가장 중요한 문서가 아니다.
						isMax = false;

						// 다음 인쇄 시도를 위해 탈출
						break;
					}
				}

				// continue하면 다음 문서가 인쇄를 시도한다.
				// 인쇄를 미뤘기 때문에 count++ 되지 않는다.
				if (!isMax) {
					continue;
				}

				// front 중요한 문서라면 인쇄했기 때문에 count++
				count++;

				// front의 위치가 찾고자 하는 문서의 위치이면 탈출
				if (front[0] == m) {
					break;
				}
			}
			
			// 스트링 빌더에 count값 담기
			sb.append(count + "\n");
		}
		
		// 출력
		System.out.println(sb);
		sc.close();
	}
}
