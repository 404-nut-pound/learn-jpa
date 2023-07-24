package io.hskim.learnjpapart2.controller;

import io.hskim.learnjpapart2.repository.OrderSearchDto;
import io.hskim.learnjpapart2.service.ItemService;
import io.hskim.learnjpapart2.service.MemberService;
import io.hskim.learnjpapart2.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;
  private final MemberService memberService;
  private final ItemService itemService;

  @GetMapping
  public String getCreateForm(Model model) {
    model.addAttribute("memberList", memberService.selectMemberAll());
    model.addAttribute("itemList", itemService.selectItemAll());

    return "order/orderForm";
  }

  @PostMapping
  public String postOrder(
    @RequestParam Long memberId,
    @RequestParam Long itemId,
    @RequestParam(name = "count") int orderCount
  ) {
    orderService.insertOrder(memberId, itemId, orderCount);

    return "redirect:/order/list";
  }

  @GetMapping(value = "/list")
  public String getOrderList(
    @ModelAttribute OrderSearchDto orderSearchDto,
    Model model
  ) {
    model.addAttribute("orderSearch", orderSearchDto);
    model.addAttribute(
      "orderList",
      orderService.selectOrderList(orderSearchDto)
    );

    return "order/orderList";
  }

  @PostMapping(value = "/{orderId}/cancel")
  public String postCancelOrder(@PathVariable Long orderId) {
    orderService.cancelOrder(orderId);

    return "redirect:/order/list";
  }
}
