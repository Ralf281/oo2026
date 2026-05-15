import React, { useEffect, useState } from 'react'
import { Button } from '@/components/ui/button'
import { toast } from "sonner"
import { Toaster } from "@/components/ui/sonner"
import { ArrowDown, ArrowUp, Check, ShoppingBag } from "lucide-react"
import { Link } from 'react-router-dom'

function Home() {

  const [categories, setCategories] = useState([])
  const [products, setProducts] = useState([])

  const [selectedCategory, setSelectedCategory] = useState("all")
  const [sort, setSort] = useState("id,asc")
  const [size, setSize] = useState(2)
  const [page, setPage] = useState(0)

  const CATEGORIES_API_URL = "http://localhost:2004/categories"

  // ---------------- CATEGORIES ----------------
  useEffect(() => {
    fetch(CATEGORIES_API_URL)
      .then(res => res.json())
      .then(json => setCategories(json))
      .catch(err => console.log(err))
  }, [])

  // ---------------- PRODUCTS (PAGEABLE BACKEND) ----------------
  useEffect(() => {
  fetch(
    `http://localhost:2004/products?page=${page}&size=${size}&sort=${sort}&keyword=${
      selectedCategory === "all" ? "" : selectedCategory
    }`
  )
    .then(res => res.json())
    .then(json => {
      setProducts(json.content)
    })
    .catch(err => console.log(err))
}, [page, size, selectedCategory, sort])

  // ---------------- SORT (ainult frontend state praegu) ----------------
  const sortAZ = () => setSort("title,asc")
  const sortZA = () => setSort("title,desc")
  const sortPriceIncreasing = () => setSort("price,asc")
  const sortPriceDecreasing = () => setSort("price,desc")

  // ---------------- CATEGORY ----------------
  const filterByCategory = (category) => {
    setSelectedCategory(category)
  }

  // ---------------- CART ----------------
  const addToCart = (product) => {
    const cartLS = JSON.parse(localStorage.getItem("cart")) || []
    cartLS.push(product)
    localStorage.setItem("cart", JSON.stringify(cartLS))
  }

  return (
    <div className="flex flex-col gap-6 pt-4">

      <h1 className="text-xl font-semibold">React Storefront</h1>

      {/* SORT */}
      <div className="flex flex-wrap gap-2">
        <Button onClick={sortAZ} variant="outline">A-Z</Button>
        <Button onClick={sortZA} variant="outline">Z-A</Button>
        <Button onClick={sortPriceIncreasing} variant="outline">
          Price <ArrowUp />
        </Button>
        <Button onClick={sortPriceDecreasing} variant="outline">
          Price <ArrowDown />
        </Button>
      </div>

      {/* CATEGORY */}
      <div className="flex items-center gap-2">
        <label>Choose category</label>
        <select onChange={(e) => filterByCategory(e.target.value)}>
          <option value="all">All</option>
          {categories.map(category => (
            <option key={category.id} value={category.name}>
              {category.name}
            </option>
          ))}
        </select>
      </div>

      {/* SIZE */}
      <div className="flex items-center gap-2">
        <label>Choose size</label>
        <select onChange={(e) => setSize(Number(e.target.value))}>
          <option value={2}>2</option>
          <option value={3}>3</option>
        </select>
      </div>

      {/* PRODUCTS COUNT */}
      <div>{products?.length || 0} items currently in stock.</div>

      {/* PRODUCTS LIST */}
      {products?.map((product, index) => (
        <div
          key={product.id}
          className="grid w-full grid-cols-[2rem_100px_minmax(0,1fr)_auto] items-center gap-4 py-8"
        >
          <div className="text-right">{index + 1}.</div>

          <img
            className="w-[100px] h-[100px] object-cover"
            src={product.image}
            alt={product.title}
          />

          <div className="min-w-0">
            <div>{product.title}</div>
            <div>{product.price}€</div>
          </div>

          <div className="justify-self-end flex gap-2">

            <Button asChild variant="outline">
              <Link to={`/product/${product.id}`}>
                View product
              </Link>
            </Button>

            <Button
              size="icon"
              onClick={() => {
                addToCart(product)
                toast("Product has been added to the cart.", {
                  icon: <Check className="h-4 w-4" />,
                })
              }}
            >
              <ShoppingBag />
            </Button>

          </div>
        </div>
      ))}

      {/* PAGINATION BUTTONS */}
      <div className="flex gap-2">
        <Button
          disabled={page === 0}
          onClick={() => setPage(prev => Math.max(prev - 1, 0))}
        >
          Prev
        </Button>

        <Button onClick={() => setPage(prev => prev + 1)}>
          Next
        </Button>
      </div>

      <Toaster position="top-center" />
    </div>
  )
}

export default Home