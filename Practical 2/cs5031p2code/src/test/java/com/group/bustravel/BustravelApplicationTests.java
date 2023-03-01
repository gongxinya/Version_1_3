package com.group.bustravel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class BustravelApplicationTests {

	private WebTestClient client;
	@BeforeEach
	void setup() {
		client = WebTestClient.bindToController(new BustravelApplication()).build();
	}

	//TODO: figure out what output is expected
	@Test
	void testListRoutes() {
		client.post().uri("/listRoutes")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody().json("");
	}

	@Test
	void testListStops() {
		client.post().uri("/listStops")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody().json("");
	}

	@Test
	void testCreateNewRoute() {
		client.post().uri("/createRoute?identifier=newR")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody().json("Created new route newR.");
	}

	@Test
	void testCreateNewStop() {
		client.post().uri("/createStop?name=newS&location=loc")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody().json("Created new stop newS.");
	}

	@Test
	void testAddStop() {
		client.post().uri("/createRoute?identifier=newR").exchange();
		client.post().uri("/createStop?name=newS&location=loc").exchange();
		client.post().uri("/addStop?route=newR&stop=newS")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody().json("Created new route newR.");
	}

	@Test
	void testListRoutesForStop() {
		client.post().uri("/createRoute?identifier=newR").exchange();
		client.post().uri("/createStop?name=newS&location=loc").exchange();
		client.post().uri("/addStop?route=newR&stop=newS").exchange();
		client.post().uri("/listRoutes?stop=newS&route=newR")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody().json(""); // TODO: add return body to this
	}

	@Test
	void testListRoutesForStopWithTime() {
		client.post().uri("/createRoute?identifier=newR").exchange();
		client.post().uri("/createStop?name=newS&location=loc").exchange();
		client.post().uri("/addStop?route=newR&stop=newS").exchange();
		client.post().uri("/listRoutes?stop=newS&route=newR&time=11:00")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody().json(""); // TODO: add return body to this
	}

	@Test
	void testListServingTime() {
		client.post().uri("/createRoute?identifier=newR").exchange();
		client.post().uri("/createStop?name=newS&location=loc").exchange();
		client.post().uri("/addStop?route=newR&stop=newS").exchange();
		client.post().uri("/listServingTime?stop=newS")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody().json(""); // TODO: add return body to this
	}

}
