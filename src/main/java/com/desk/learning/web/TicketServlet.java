package com.desk.learning.web;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.desk.learning.dao.TicketDao;
import com.desk.learning.model.MessageModel;
import com.desk.learning.model.TicketModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/TicketServlet")
public class TicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TicketDao ticketDao;

	public TicketServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		ticketDao = new TicketDao();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String primaryId = request.getParameter("id");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		try {
			Integer id = Integer.parseInt(primaryId);
			TicketModel ticket = ticketDao.getTicket(id);
			ticketDao.insertUser(ticket);
			String jsonString = mapper.writeValueAsString(ticket);
			response.setStatus(200);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.write(jsonString);
		} catch (Exception e) {
			System.out.println(e);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(400);
			String jsonString = mapper.writeValueAsString(e);
			out.write(jsonString);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doOptions(request, response);
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		try {
			JSONObject json = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
			Integer id = json.getInt("id");
			String name = json.getString("name");
			String priority = json.getString("priority");
			String medium = json.getString("medium");
			String email = json.getString("email");
			String phone = json.getString("phone");
			TicketModel ticket = new TicketModel(id, name, priority, medium, email, phone);
			ticketDao.insertUser(ticket);
			MessageModel message = new MessageModel("Ticket Created");
			String jsonString = mapper.writeValueAsString(message);
			response.setStatus(200);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.write(jsonString);
		} catch (JSONException e) {
			System.out.println(e);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(400);
			String jsonString = mapper.writeValueAsString(e);
			out.write(jsonString);

		} catch (Exception e) {
			System.out.println(e);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(400);
			String jsonString = mapper.writeValueAsString(e);
			out.write(jsonString);
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doOptions(request, response);
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		try {

			JSONObject json = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
			Integer primaryId = json.getInt("primaryId");
			Integer id = json.getInt("id");
			String name = json.getString("name");
			String priority = json.getString("priority");
			String medium = json.getString("medium");
			String email = json.getString("email");
			String phone = json.getString("phone");
			TicketModel ticket = new TicketModel(primaryId, id, name, priority, medium, email, phone);
			boolean isUpdated = ticketDao.updateTicket(ticket);
			if (isUpdated) {
				MessageModel message = new MessageModel("Ticket Updated");
				String jsonString = mapper.writeValueAsString(message);
				response.setStatus(200);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				out.write(jsonString);
			} else {
				throw new Exception();
			}
		} catch (JSONException e) {
			System.out.println(e);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(400);
			String jsonString = mapper.writeValueAsString(e);
			out.write(jsonString);

		} catch (Exception e) {
			System.out.println(e);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(400);
			String jsonString = mapper.writeValueAsString(e);
			out.write(jsonString);
		}

	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.print("inside delete try");
			JSONObject json = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
			int primaryId = json.getInt("primaryId");
			Integer id = json.getInt("id");
			boolean isUpdated = ticketDao.deleteTicket(primaryId, id);
			if (isUpdated) {
				MessageModel message = new MessageModel("Ticket deleted");
				String jsonString = mapper.writeValueAsString(message);
				response.setStatus(200);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				out.write(jsonString);
			} else {
				throw new Exception();
			}
		} catch (JSONException e) {
			System.out.println(e);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(400);
			String jsonString = mapper.writeValueAsString(e);
			out.write(jsonString);

		} catch (Exception e) {
			System.out.println(e);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(400);
			String jsonString = mapper.writeValueAsString(e);
			out.write(jsonString);
		}
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doOptions(request, response);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
		response.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");
		response.setContentType("application/json");
	}

}
