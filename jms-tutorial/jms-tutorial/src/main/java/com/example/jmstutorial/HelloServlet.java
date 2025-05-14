package com.example.jmstutorial;

import java.io.*;

import jakarta.annotation.Resource;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/send")
public class HelloServlet extends HttpServlet {

    @Resource(lookup = "java:/jms/queue/TestQueue")
    private Queue queue;

    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try (JMSContext context = connectionFactory.createContext()) {
            context.createProducer().send(queue, "Hello from Web Servlet!");
            resp.getWriter().write("Message sent to queue.");
        }
    }
}