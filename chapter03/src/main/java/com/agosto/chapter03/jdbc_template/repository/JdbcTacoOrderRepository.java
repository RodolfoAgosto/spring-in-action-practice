package com.agosto.chapter03.jdbc_template.repository;

import com.agosto.chapter03.jdbc_template.entity.Ingredient;
import com.agosto.chapter03.jdbc_template.entity.IngredientRef;
import com.agosto.chapter03.jdbc_template.entity.Taco;
import com.agosto.chapter03.jdbc_template.entity.TacoOrder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
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

    JdbcTemplate jdbcTemplate;

    JdbcTacoOrderRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder tacoOrder) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Taco_Order (" +
                        "delivery_Name, delivery_Street, delivery_City, " +
                        "delivery_State, delivery_Zip, cc_Number, " +
                        "cc_Expiration, cc_CVV, placed_at)" +
                        " values(?,?,?,?,?,?,?,?,?)",
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);
        tacoOrder.setPlacedAt(new Date());
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
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
        jdbcTemplate.update(psc, keyHolder);
        tacoOrder.setId(keyHolder.getKey().longValue());
        List<Taco> tacos =  tacoOrder.getTacos();
        int keyTaco = 0;
        for (Taco taco : tacos) {
            saveTaco(tacoOrder.getId(), keyTaco++, taco);
        }
        return tacoOrder;

    }

    public void saveTaco(Long idOrder, int taco_order_key, Taco taco){

        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Taco(name, taco_order, taco_order_key, created_at) values ( ?, ?, ?, ?)",
        Types.VARCHAR, Types.BIGINT, Types.BIGINT, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
           taco.getName(),idOrder, taco_order_key, taco.getCreatedAt()
        ));
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, generatedKeyHolder);
        taco.setId(generatedKeyHolder.getKey().longValue());
        saveIngredientRef(taco.getId(), taco.getIngredients());
    }

    public void saveIngredientRef(Long tacoID, List<IngredientRef> ingredients){

        int taco_key = 0;
        for(IngredientRef ingredientRef : ingredients) {
            PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                    "insert into Ingredient_Ref (ingredient, taco, taco_key) values ???",
                    Types.VARCHAR, Types.BIGINT, Types.BIGINT
            );
            pscf.setReturnGeneratedKeys(true);
            PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
                    ingredientRef.getIngredient(), tacoID, taco_key++
            ));
        }
    }

    @Override
    public Optional<TacoOrder> findById(Long id) {
        List<TacoOrder> tacoOrders = jdbcTemplate.query("select * from TacoOrder", this::mapRowToTacoOrder);
        return tacoOrders.size() == 0 ? Optional.empty() : Optional.of(tacoOrders.get(0));
    }

    private TacoOrder mapRowToTacoOrder(ResultSet row, int rowNumber) throws SQLException {
        TacoOrder tacoOrder =  new TacoOrder();
        tacoOrder.setId(row.getLong("id"));
        tacoOrder.setDeliveryName(row.getString("delivery_Name"));
        tacoOrder.setDeliveryStreet(row.getString("delivery_Street"));
        tacoOrder.setDeliveryCity(row.getString("delivery_City"));
        tacoOrder.setDeliveryState(row.getString("delivery_State"));
        tacoOrder.setDeliveryZip(row.getString("delivery_Zip"));
        tacoOrder.setCcNumber(row.getString("cc_number"));
        tacoOrder.setCcExpiration(row.getString("cc_expiration"));
        tacoOrder.setCcCVV(row.getString("cc_cvv"));
        tacoOrder.setPlacedAt(new Date(row.getTimestamp("placed_at").getTime()));
        return tacoOrder;
    }

}
