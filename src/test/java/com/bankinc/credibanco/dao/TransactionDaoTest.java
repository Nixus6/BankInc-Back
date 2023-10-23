/*
 * package com.bankinc.credibanco.dao;
 * 
 * import static org.assertj.core.api.Assertions.assertThat;
 * 
 * import java.util.Calendar;
 * 
 * import org.junit.jupiter.api.BeforeEach; import
 * org.junit.jupiter.api.DisplayName; import org.junit.jupiter.api.Test; import
 * org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
 * import
 * org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.
 * Replace; import
 * org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest; import
 * com.bankinc.credibanco.model.Tarjet; import
 * com.bankinc.credibanco.model.Transaction; import
 * com.bankinc.credibanco.model.TransactionState; import
 * com.bankinc.credibanco.model.User; import
 * com.bankinc.credibanco.model.dao.ITarjetDao; import
 * com.bankinc.credibanco.model.dao.ITransactionDao; import
 * com.bankinc.credibanco.model.dao.IUserDao;
 * 
 * @DataJpaTest //@SpringBootTest(classes=CredibancoApplication.class)
 * 
 * @AutoConfigureTestDatabase(replace = Replace.NONE) public class
 * TransactionDaoTest { private static final Logger log =
 * LoggerFactory.getLogger(TransactionDaoTest.class);
 * 
 * @Autowired private ITransactionDao transactionDao;
 * 
 * @Autowired private ITarjetDao tarjetDao;
 * 
 * @Autowired private IUserDao userDao;
 * 
 * private Transaction transaction;
 * 
 * private Calendar date = Calendar.getInstance();
 * 
 * private User user;
 * 
 * private Tarjet tarjet;
 * 
 * @BeforeEach void setup() { user = userDao.findById(1).orElse(new User());
 * tarjet = tarjetDao.findById(4).orElse(new Tarjet()); transaction =
 * Transaction.builder().totalPrice(1000).state(TransactionState.SUCCESSFUL).
 * fechaTransaccion(date) .usuario(user).tarjet(tarjet).build();
 * 
 * }
 * 
 * @DisplayName("Test para guardar una transaccion")
 * 
 * @Test void testSaveTransaction() {
 * 
 * // given - dado o condición previa o configuración Transaction transaction1 =
 * Transaction.builder().totalPrice(1000).state(TransactionState.SUCCESSFUL)
 * .fechaTransaccion(date).usuario(user).tarjet(tarjet).build();
 * 
 * // when - acción o el comportamiento que vamos a probar Transaction
 * saveTransaction = transactionDao.save(transaction1);
 * log.info("entro saveTransaction : "+ saveTransaction );
 * 
 * // then - verificar la salida assertThat(saveTransaction).isNotNull();
 * assertThat(saveTransaction.getId()).isGreaterThan(0); }
 * 
 * }
 */