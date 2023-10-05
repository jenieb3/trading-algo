package codingblackfemales.gettingstarted;
import codingblackfemales.action.Action;
import codingblackfemales.action.CancelChildOrder;
import codingblackfemales.action.CreateChildOrder;
import codingblackfemales.action.NoAction;
import codingblackfemales.algo.AlgoLogic;
import codingblackfemales.sotw.ChildOrder;
import codingblackfemales.sotw.SimpleAlgoState;
import codingblackfemales.sotw.marketdata.BidLevel;
import codingblackfemales.util.Util;
import messages.order.Side;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class MyAlgoLogic implements AlgoLogic {

    private static final Logger logger = LoggerFactory.getLogger(MyAlgoLogic.class);
    private int ordersCreated = 0;

    @Override
    public Action evaluate(SimpleAlgoState state) {

        var orderBookAsString = Util.orderBookToString(state);

        logger.info("[MYALGO] The state of the order book is:\n" + orderBookAsString);

        final BidLevel nearTouch = state.getBidAt(0);
        long quantity = 100;
        long price = nearTouch.price;
        List<ChildOrder> existingOrders = state.getChildOrders();
        // Create up to three child orders
        if (ordersCreated < 3) {
            logger.info("[MYALGO] Have: " + state.getChildOrders().size() + " children, want 3, joining passive side of book with: " + quantity + " @ " + price);
            ordersCreated++;
            return new CreateChildOrder(Side.BUY, quantity, price);
        }
        // cancel oldest order
        else if(existingOrders.size() > 10) {
            ChildOrder oldestOrder = existingOrders.get(0);
            logger.info("[MYALGO] Have more than 10 child orders, cancelling the oldest order " + (oldestOrder));
            return new CancelChildOrder(oldestOrder);
            }
        return NoAction.NoAction;
        }
    }








