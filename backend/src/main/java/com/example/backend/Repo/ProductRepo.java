package com.example.backend.Repo;

import com.example.backend.DTO.ProductDTO;
import com.example.backend.Entity.Product;
import com.example.backend.Projection.ProductProjection;
import com.example.backend.Projection.ProductProjectionGetAll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepo extends JpaRepository<Product, UUID> {
    List<Product> findAll();

    List<Product> findAllByNameContainingIgnoreCaseOrCodeOrCategory_Id(String name, String code, UUID categoryId);

    @Query(value = "SELECT\n" +
            "    p.name AS name,\n" +
            "    p.price AS price,\n" +
            "    p.code AS code,\n" +
            "    ai.id AS img_id,\n" +
            "    ip.income_price AS income_price,\n" +
            "    SUM(COALESCE(ip_count_sum, 0) - COALESCE(ep_count_sum, 0)) AS balance\n" +
            "FROM\n" +
            "    product p\n" +
            "        INNER JOIN (\n" +
            "        SELECT ip.product_id, SUM(ip.count) AS ip_count_sum, ip.income_price\n" +
            "        FROM income_product ip\n" +
            "        GROUP BY ip.product_id, ip.income_price\n" +
            "    ) ip ON p.id = ip.product_id\n" +
            "        INNER JOIN avatar_img ai ON ai.id = p.img_url_id\n" +
            "        LEFT JOIN (\n" +
            "        SELECT ep.product_id, SUM(ep.count) AS ep_count_sum\n" +
            "        FROM expense_product ep\n" +
            "        GROUP BY ep.product_id\n" +
            "    ) ep ON p.id = ep.product_id\n" +
            "where p.id = cast(:id as uuid)\n" +
            "GROUP BY\n" +
            "    p.name,\n" +
            "    p.price,\n" +
            "    p.code,\n" +
            "    ai.id,\n" +
            "    ip.income_price;\n", nativeQuery = true)
    ProductProjection getProductCard(UUID id);
    @Query(value = "SELECT\n" +
            "    p.id AS product_id,\n" +
            "    p.name AS name,\n" +
            "    p.price AS price,\n" +
            "    p.code AS code,\n" +
            "    JSONB_BUILD_OBJECT('id',ai.id, 'img_url',encode(lo_get(cast(ai.img_url AS OID)), 'base64')) AS img_url,\n" +
            "    cat.id AS category_id,\n" +
            "    cat.name AS category_name,\n" +
            "    SUM(COALESCE(ip_count_sum, 0) - COALESCE(ep_count_sum, 0)) AS balance\n" +
            "FROM\n" +
            "    product p\n" +
            "        INNER JOIN (\n" +
            "        SELECT product_id, SUM(count) AS ip_count_sum\n" +
            "        FROM income_product\n" +
            "        GROUP BY product_id\n" +
            "    ) ip ON p.id = ip.product_id\n" +
            "        INNER JOIN avatar_img ai ON ai.id = p.img_url_id\n" +
            "        LEFT JOIN (\n" +
            "        SELECT product_id, SUM(count) AS ep_count_sum\n" +
            "        FROM expense_product\n" +
            "        GROUP BY product_id\n" +
            "    ) ep ON p.id = ep.product_id\n" +
            "        JOIN category cat ON p.category_id = cat.id\n" +
            "GROUP BY\n" +
            "    p.id,\n" +
            "    p.name,\n" +
            "    ai.id,\n" +
            "    cat.id,\n" +
            "    cat.name  " +
            "order by p.name;\n", nativeQuery = true)
    List<ProductProjectionGetAll> getCountOfAllProducts();
    @Query(value = "SELECT\n" +
            "    p.id AS product_id,\n" +
            "    p.name AS name,\n" +
            "    p.price AS price,\n" +
            "    p.code AS code,\n" +
            "    JSONB_BUILD_OBJECT('id', ai.id, 'img_url', encode(lo_get(cast(ai.img_url AS OID)), 'base64')) AS img_url,\n" +
            "    cat.id AS category_id,\n" +
            "    cat.name AS category_name,\n" +
            "    SUM(COALESCE(ip_count_sum, 0) - COALESCE(ep_count_sum, 0)) AS balance\n" +
            "FROM\n" +
            "    product p\n" +
            "        INNER JOIN (\n" +
            "        SELECT product_id, SUM(count) AS ip_count_sum\n" +
            "        FROM income_product\n" +
            "        GROUP BY product_id\n" +
            "    ) ip ON p.id = ip.product_id\n" +
            "        INNER JOIN avatar_img ai ON ai.id = p.img_url_id\n" +
            "        LEFT JOIN (\n" +
            "        SELECT product_id, SUM(count) AS ep_count_sum\n" +
            "        FROM expense_product\n" +
            "        GROUP BY product_id\n" +
            "    ) ep ON p.id = ep.product_id\n" +
            "        JOIN category cat ON p.category_id = cat.id\n" +
            "where p.code = :code\n" +
            "GROUP BY\n" +
            "    p.id,\n" +
            "    p.name,\n" +
            "    ai.id,\n" +
            "    cat.id,\n" +
            "    cat.name;\n", nativeQuery = true)
    ProductProjectionGetAll getByCode(String code);
    Product findByCode(String code);
    @Query(value = "SELECT\n" +
            "    p.id AS product_id,\n" +
            "    p.name AS name,\n" +
            "    p.price AS price,\n" +
            "    p.code AS code,\n" +
            "    JSONB_BUILD_OBJECT('id', ai.id, 'img_url', encode(lo_get(cast(ai.img_url AS OID)), 'base64')) AS img_url,\n" +
            "    cat.id AS category_id,\n" +
            "    cat.name AS category_name,\n" +
            "    SUM(COALESCE(ip_count_sum, 0) - COALESCE(ep_count_sum, 0)) AS balance\n" +
            "FROM\n" +
            "    product p\n" +
            "        INNER JOIN (\n" +
            "        SELECT product_id, SUM(count) AS ip_count_sum\n" +
            "        FROM income_product\n" +
            "        GROUP BY product_id\n" +
            "    ) ip ON p.id = ip.product_id\n" +
            "        INNER JOIN avatar_img ai ON ai.id = p.img_url_id\n" +
            "        LEFT JOIN (\n" +
            "        SELECT product_id, SUM(count) AS ep_count_sum\n" +
            "        FROM expense_product\n" +
            "        GROUP BY product_id\n" +
            "    ) ep ON p.id = ep.product_id\n" +
            "        JOIN category cat ON p.category_id = cat.id\n" +
            "where p.category_id = :categoryId and lower(p.name) like LOWER('%'||:name || '%')\n" +
            "GROUP BY\n" +
            "    p.id,\n" +
            "    p.name,\n" +
            "    ai.id,\n" +
            "    cat.id,\n" +
            "    cat.name " +
            "order by p.name;\n", nativeQuery = true)
    List<ProductProjectionGetAll> findAllByCategory_IdAndNameContainingIgnoreCase(UUID categoryId, String name);
    @Query(value = "SELECT\n" +
            "    p.id AS product_id,\n" +
            "    p.name AS name,\n" +
            "    p.price AS price,\n" +
            "    p.code AS code,\n" +
            "    JSONB_BUILD_OBJECT('id', ai.id, 'img_url', encode(lo_get(cast(ai.img_url AS OID)), 'base64')) AS img_url,\n" +
            "    cat.id AS category_id,\n" +
            "    cat.name AS category_name,\n" +
            "    SUM(COALESCE(ip_count_sum, 0) - COALESCE(ep_count_sum, 0)) AS balance\n" +
            "FROM\n" +
            "    product p\n" +
            "        INNER JOIN (\n" +
            "        SELECT product_id, SUM(count) AS ip_count_sum\n" +
            "        FROM income_product\n" +
            "        GROUP BY product_id\n" +
            "    ) ip ON p.id = ip.product_id\n" +
            "        INNER JOIN avatar_img ai ON ai.id = p.img_url_id\n" +
            "        LEFT JOIN (\n" +
            "        SELECT product_id, SUM(count) AS ep_count_sum\n" +
            "        FROM expense_product\n" +
            "        GROUP BY product_id\n" +
            "    ) ep ON p.id = ep.product_id\n" +
            "        JOIN category cat ON p.category_id = cat.id\n" +
            "WHERE\n" +
            "        p.category_id = :categoryId\n" +
            "  AND similarity(LOWER(p.name), :name) > :percent\n" +
            "GROUP BY\n" +
            "    p.id,\n" +
            "    p.name,\n" +
            "    ai.id,\n" +
            "    cat.id,\n" +
            "    cat.name\n" +
            "ORDER BY\n" +
            "    p.name;\n" +
            "\n", nativeQuery = true)
    List<ProductProjectionGetAll> findAllByCategory_IdAndNameContainingIgnoreCaseSimilarity(UUID categoryId, String name, double percent);

    @Query(value = "SELECT\n" +
            "    p.id AS product_id,\n" +
            "    p.name AS name,\n" +
            "    p.price AS price,\n" +
            "    p.code AS code,\n" +
            "    JSONB_BUILD_OBJECT('id', ai.id, 'img_url', encode(lo_get(cast(ai.img_url AS OID)), 'base64')) AS img_url,\n" +
            "    cat.id AS category_id,\n" +
            "    cat.name AS category_name,\n" +
            "    SUM(COALESCE(ip_count_sum, 0) - COALESCE(ep_count_sum, 0)) AS balance\n" +
            "FROM\n" +
            "    product p\n" +
            "        INNER JOIN (\n" +
            "        SELECT product_id, SUM(count) AS ip_count_sum\n" +
            "        FROM income_product\n" +
            "        GROUP BY product_id\n" +
            "    ) ip ON p.id = ip.product_id\n" +
            "        INNER JOIN avatar_img ai ON ai.id = p.img_url_id\n" +
            "        LEFT JOIN (\n" +
            "        SELECT product_id, SUM(count) AS ep_count_sum\n" +
            "        FROM expense_product\n" +
            "        GROUP BY product_id\n" +
            "    ) ep ON p.id = ep.product_id\n" +
            "        JOIN category cat ON p.category_id = cat.id\n" +
            "where p.category_id = :categoryId\n" +
            "GROUP BY\n" +
            "    p.id,\n" +
            "    p.name,\n" +
            "    ai.id,\n" +
            "    cat.id,\n" +
            "    cat.name " +
            "order by p.name;\n", nativeQuery = true)
    List<ProductProjectionGetAll> findAllByCategory_Id(UUID categoryId);
    @Query(value = "SELECT\n" +
            "    p.id AS product_id,\n" +
            "    p.name AS name,\n" +
            "    p.price AS price,\n" +
            "    p.code AS code,\n" +
            "    JSONB_BUILD_OBJECT('id', ai.id, 'img_url', encode(lo_get(cast(ai.img_url AS OID)), 'base64')) AS img_url,\n" +
            "    cat.id AS category_id,\n" +
            "    cat.name AS category_name,\n" +
            "    SUM(COALESCE(ip_count_sum, 0) - COALESCE(ep_count_sum, 0)) AS balance\n" +
            "FROM\n" +
            "    product p\n" +
            "        INNER JOIN (\n" +
            "        SELECT product_id, SUM(count) AS ip_count_sum\n" +
            "        FROM income_product\n" +
            "        GROUP BY product_id\n" +
            "    ) ip ON p.id = ip.product_id\n" +
            "        INNER JOIN avatar_img ai ON ai.id = p.img_url_id\n" +
            "        LEFT JOIN (\n" +
            "        SELECT product_id, SUM(count) AS ep_count_sum\n" +
            "        FROM expense_product\n" +
            "        GROUP BY product_id\n" +
            "    ) ep ON p.id = ep.product_id\n" +
            "        JOIN category cat ON p.category_id = cat.id\n" +
            "where lower(p.name) like LOWER('%'||:name || '%')\n" +
            "GROUP BY\n" +
            "    p.id,\n" +
            "    p.name,\n" +
            "    ai.id,\n" +
            "    cat.id,\n" +
            "    cat.name " +
            "order by p.name;\n", nativeQuery = true)
    List<ProductProjectionGetAll> findAllByNameContainingIgnoreCase(String name);
    @Query(value = "SELECT\n" +
            "    p.id AS product_id,\n" +
            "    p.name AS name,\n" +
            "    p.price AS price,\n" +
            "    p.code AS code,\n" +
            "    JSONB_BUILD_OBJECT('id', ai.id, 'img_url', encode(lo_get(cast(ai.img_url AS OID)), 'base64')) AS img_url,\n" +
            "    cat.id AS category_id,\n" +
            "    cat.name AS category_name,\n" +
            "    SUM(COALESCE(ip_count_sum, 0) - COALESCE(ep_count_sum, 0)) AS balance\n" +
            "FROM\n" +
            "    product p\n" +
            "        INNER JOIN (\n" +
            "        SELECT product_id, SUM(count) AS ip_count_sum\n" +
            "        FROM income_product\n" +
            "        GROUP BY product_id\n" +
            "    ) ip ON p.id = ip.product_id\n" +
            "        INNER JOIN avatar_img ai ON ai.id = p.img_url_id\n" +
            "        LEFT JOIN (\n" +
            "        SELECT product_id, SUM(count) AS ep_count_sum\n" +
            "        FROM expense_product\n" +
            "        GROUP BY product_id\n" +
            "    ) ep ON p.id = ep.product_id\n" +
            "        JOIN category cat ON p.category_id = cat.id\n" +
            "WHERE EXISTS (\n" +
            "    SELECT 1\n" +
            "    FROM unnest(string_to_array(p.name, ' ')) AS part\n" +
            "    WHERE similarity(part, :name) > :percent\n" +
            ")\n" +
            "GROUP BY\n" +
            "    p.id,\n" +
            "    p.name,\n" +
            "    ai.id,\n" +
            "    cat.id,\n" +
            "    cat.name\n" +
            "ORDER BY p.name;", nativeQuery = true)
    List<ProductProjectionGetAll> findAllByNameSimilarity(String name, double percent);
}
