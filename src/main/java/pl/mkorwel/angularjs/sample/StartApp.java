package pl.mkorwel.angularjs.sample;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
import static spark.Spark.patch;
import static spark.SparkBase.staticFileLocation;

import com.google.gson.Gson;

import pl.mkorwel.angularjs.sample.domain.User;
import pl.mkorwel.angularjs.sample.domain.UserFilter;
import pl.mkorwel.angularjs.sample.store.UserStore;
import spark.Request;

public class StartApp {

	private static final UserStore store = new UserStore();

	private static final Gson gson = new Gson();

	public static void main(String[] args) {

		staticFileLocation("/public");

		get("/rest/users", "application/json", (req, res) -> {
			return store.getAll(new UserFilter(req.queryParams("filterName"),
					req.queryParams("filterStatus")));
		}, new JsonTransformer());

		get("/rest/users/:id", "application/json",
				(req, res) -> store.get(getIdParam(req)), new JsonTransformer());

		post("/rest/users", (req, res) -> {
			User user = store.save(gson.fromJson(req.body(), User.class));
			res.status(201);
			res.header("Location", "/rest/users/" + user.getId());
			return "";
		});

		put("/rest/users/:id", (req, res) -> {
			User user = gson.fromJson(req.body(), User.class);
			user.setId(getIdParam(req));
			return store.save(user);
		});

		delete("/rest/users/:id", (req, res) -> {
			store.delete(getIdParam(req));
			res.status(204);
			return "";
		});

		patch("/rest/users/:id/activate", (req, res) -> {
			store.activate(getIdParam(req));
			res.status(204);
			return "";
		});
	}

	private static long getIdParam(Request req) {
		return Long.parseLong(req.params(":id"));
	}
}
