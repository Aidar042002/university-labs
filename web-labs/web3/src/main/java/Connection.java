import org.primefaces.PrimeFaces;

import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@ManagedBean(name = "db")
@ApplicationScoped
public class Connection {

    private Point point = new Point();


    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    private static EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("webLabPU");
    }

    private List<Point> results;

    public List<Point> getResults() {
        return results;
    }

    private EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    private void closeEntityManager(EntityManager em) {
        try {
            if (em != null && em.isOpen()) {
                em.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Point> getAllResults() {
        try {
            EntityManager em = createEntityManager();
            results = em.createQuery("SELECT r FROM Point r", Point.class).getResultList();
            closeEntityManager(em);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String saveData() {
        try {
            EntityManager em = createEntityManager();
            em.getTransaction().begin();

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

            String x = request.getParameter("x");
            String y = request.getParameter("y");
            String r = request.getParameter("r");
            String hit = "-";
            double xDouble = Double.parseDouble(x);
            double yDouble = Double.parseDouble(y);
            double rDouble = Double.parseDouble(r);
            if (checkCircle(xDouble, yDouble, rDouble) || checkRectangle(xDouble, yDouble, rDouble) || checkTriangle(xDouble, yDouble, rDouble)) {
                hit = "+";
            }

            Point point = new Point();
            point.setX(x);
            point.setY(y);
            point.setR(r);
            point.setHit(hit);

            em.persist(point);
            em.getTransaction().commit();

            results = getAllResults();
            PrimeFaces.current().ajax().update("tableForm:table");
            closeEntityManager(em);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public boolean checkRectangle(double x, double y, double r) {
        return x >= 0 && x <= r && y >= 0 && y <= r && x <= r / 2 && y <= r;
    }

    public boolean checkCircle(double x, double y, double r) {
        if (x < 0 && y > 0) {
            double distanceToOrigin = Math.sqrt(x * x + y * y);
            return distanceToOrigin <= r;
        }
        return false;
    }

    public boolean checkTriangle(double x, double y, double r) {
        return x <= 0 && y <= 0 && y >= (-x - r);
    }

    public void deleteAllResults() {
        try {
            EntityManager em = createEntityManager();
            em.getTransaction().begin();

            em.createQuery("DELETE FROM Point").executeUpdate();

            em.getTransaction().commit();

            results = getAllResults();
            PrimeFaces.current().ajax().update("tableForm:table");
            PrimeFaces.current().executeScript("localStorage.clear();location.reload(true);");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
