package com.agosto.chapter03.jdbc_template.repository;

import com.agosto.chapter03.jdbc_template.entity.Ingredient;
import com.agosto.chapter03.jdbc_template.entity.IngredientRef;
import com.agosto.chapter03.jdbc_template.entity.Taco;
import com.agosto.chapter03.jdbc_template.entity.TacoOrder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTacoOrderRepository implements TacoOrderRepository {

    private JdbcOperations jdbcOperations;

    public JdbcTacoOrderRepository(JdbcTemplate jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder tacoOrder) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into TACO_ORDER (" +
                        "DELIVERY_NAME, DELIVERY_STREET, DELIVERY_CITY, " +
                        "DELIVERY_STATE, DELIVERY_ZIP, CC_NUMBER, " +
                        "CC_EXPIRATION, CC_CVV, PLACED_AT)" +
                        " values(?,?,?,?,?,?,?,?,?)",
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);

        tacoOrder.setPlacedAt(new Date());
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                                       tacoOrder.getDeliveryName(),
                                       tacoOrder.getDeliveryStreet(),
                                       tacoOrder.getDeliveryCity(),
                                       tacoOrder.getDeliveryState(),
                                       tacoOrder.getDeliveryZip(),
                                       tacoOrder.getCcNumber(),
                                       tacoOrder.getCcExpiration(),
                                       tacoOrder.getCcCVV(),
                                       tacoOrder.getPlacedAt()));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        tacoOrder.setId(keyHolder.getKey().longValue());

        List<Taco> tacos =  tacoOrder.getTacos();
        int keyTaco = 0;
        for (Taco taco : tacos) {
            saveTaco(tacoOrder.getId(), keyTaco++, taco);
        }
        return tacoOrder;

    }

    public long saveTaco(Long idOrder, int taco_order_key, Taco taco){

        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into TACO(NAME, TACO_ORDER, TACO_ORDER_KEY, CREATED_AT) values ( ?, ?, ?, ?)",
        Types.VARCHAR, Types.BIGINT, Types.BIGINT, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                       taco.getName(),idOrder, taco_order_key, taco.getCreatedAt()
        ));
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, generatedKeyHolder);
        taco.setId(generatedKeyHolder.getKey().longValue());

        saveIngredientRef(taco.getId(), taco.getIngredients());

        return taco.getId();
    }

    public void saveIngredientRef(long tacoId, List<IngredientRef> ingredientRefs){

        int taco_key = 0;
        for(IngredientRef ingredientRef : ingredientRefs) {
                jdbcOperations.update(
                        "insert into Ingredient_Ref (ingredient, taco, taco_key) "
                                + "values (?, ?, ?)",
                        ingredientRef.getIngredient(), tacoId, taco_key++);
            }
    }

    @Override
    public Optional<TacoOrder> findById(Long id) {
        TacoOrder tacoOrder = jdbcOperations.queryForObject("select ID, DELIVERY_NAME, DELIVERY_STREET, DELIVERY_CITY, "
                + "DELIVERY_STATE, DELIVERY_ZIP, CC_NUMBER, CC_EXPIRATION, CC_CVV, PLACED_AT from TACO_ORDER where id=?", this::mapRowToTacoOrder, id);
        return Optional.of(tacoOrder);
    }

    private TacoOrder mapRowToTacoOrder(ResultSet row, int rowNumber) throws SQLException {
        TacoOrder tacoOrder =  new TacoOrder();
        tacoOrder.setId(row.getLong("id"));
        tacoOrder.setDeliveryName(row.getString("DELIVERY_NAME"));
        tacoOrder.setDeliveryStreet(row.getString("DELIVERY_STREET"));
        tacoOrder.setDeliveryCity(row.getString("DELIVERY_CITY"));
        tacoOrder.setDeliveryState(row.getString("DELIVERY_STATE"));
        tacoOrder.setDeliveryZip(row.getString("DELIVERY_ZIP"));
        tacoOrder.setCcNumber(row.getString("CC_NUMBER"));
        tacoOrder.setCcExpiration(row.getString("CC_EXPIRATION"));
        tacoOrder.setCcCVV(row.getString("CC_CVV"));
        tacoOrder.setPlacedAt(new Date(row.getTimestamp("PLACED_AT").getTime()));
        return tacoOrder;
    }

    private List<Taco> findTacosByOrderId(long orderId) {
        return jdbcOperations.query(
                "select id, name, created_at from Taco "
                        + "where taco_order=? order by taco_order_key",
                (row, rowNum) -> {
                    Taco taco = new Taco();
                    taco.setId(row.getLong("id"));
                    taco.setName(row.getString("name"));
                    taco.setCreatedAt(new Date(row.getTimestamp("created_at").getTime()));
                    taco.setIngredients(findIngredientsByTacoId(row.getLong("id")));
                    return taco;
                },
                orderId);
    }

    private List<IngredientRef> findIngredientsByTacoId(long tacoId) {
        return jdbcOperations.query(
                "select ingredient from Ingredient_Ref "
                        + "where taco = ? order by taco_key",
                (row, rowNum) -> {
                    return new IngredientRef(row.getString("ingredient"));
                },
                tacoId);
    }


}
